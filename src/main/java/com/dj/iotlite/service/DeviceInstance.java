package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.utils.JsonUtils;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class DeviceInstance implements DeviceModel {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    PushService pushService;

    @Autowired
    DeviceLogService deviceLogService;

    @Override
    public void setProductSn(String productSn) {

    }

    @Autowired
    MqttClient mqttClient;

    @Override
    public void setProperty(String productSn, String deviceSn, String property, Object value, String desc) {
        Map<String, Object> propertys = new HashMap<>();
        propertys.put(property, value);
        setPropertys(productSn, deviceSn, propertys, desc);
    }

    @Override
    public void setPropertys(String productSn, String deviceSn, Map<String, Object> propertys, String desc) {

        Product product = productRepository.findFirstBySn(productSn).orElseThrow(() -> {
            throw new BusinessException("产品序号不存在");
        });

        Device device = deviceRepository.findFirstBySnAndProductSn(deviceSn, productSn).orElseThrow(() -> {
            throw new BusinessException("设备序号不存在");
        });

        Gson gson = new Gson();
        String topic = String.format(RedisKey.DeviceProperty, "default", productSn, deviceSn);
        propertys.put("v", device.getVersion());
        String data = gson.toJson(propertys);

        device.setVersion(device.getVersion() + 1);
        deviceRepository.save(device);
        //写入下发日志
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(data.getBytes(StandardCharsets.UTF_8));
        deviceLogService.Log(deviceSn, productSn, DirectionEnum.Down, "admin", topic, desc, JsonUtils.toJson(propertys));
        try {
            log.info("topic {}  data: {}", topic, data);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new BusinessException("下发设备信息异常");
        }
    }


    @Autowired
    RedisCommands<String, String> redisCommands;

    public void deviceResponse(String productSn, String deviceSn, String topic,  String rawData) throws Exception {
        Integer v = JsonPath.read(rawData, "$.v");
        //TODO 并发问题处理
        deviceRepository.findFirstBySnAndProductSn(deviceSn, productSn).ifPresent((d) -> {
            d.setVersion(v);
            deviceRepository.save(d);
        });
    }

    @Autowired
    GroupInstance groupInstance;

    public void devicePropertyChange(String productSn, String deviceSn, String topic, String data) {
        log.info("设备属性变化  更新");
        String name = JsonPath.read(data, "$.name");
        String value = JsonPath.read(data, "$.value");
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);
        redisCommands.hset(member, name, value);
        var groupName = redisCommands.hget(member, "deviceGroup");
        Arrays.stream(groupName.split(",")).forEach(g -> {
            groupInstance.observed(g, productSn, deviceSn, name, value);
        });
    }


    public void deviceEventFire(String productSn, String deviceSn, String topic, String rawData) {
        log.info("设备发生事件  更新");
        String name = JsonPath.read(rawData, "$.name");
        Object payload = null;
        try {
            payload = JsonPath.read(rawData, "$.payload");
        } catch (Exception e) {
            log.info("payload is empty");
        }
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);

        //TODO 设备组编排
        var groupName = redisCommands.hget(member, "deviceGroup");
        if (!ObjectUtils.isEmpty(groupName)) {
            for (String g : groupName.split(",")) {
                groupInstance.fire(g, productSn, deviceSn, name, payload);
            }
        }
    }

    public void deviceAlarm(String productSn, String deviceSn, String topic, String rawData) {
        log.info("设备发生告警  更新");
        String name = JsonPath.read(rawData, "$.name");
        Object payload = null;
        try {
            payload = JsonPath.read(rawData, "$.payload");
        } catch (Exception e) {
            log.info("payload is empty");
        }
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);
        var groupName = redisCommands.hget(member, "deviceGroup");
        for (String g : groupName.split(",")) {
            groupInstance.fire(g, productSn, deviceSn, name, payload);
        }
    }

    public void deviceLog(String productSn, String deviceSn, String topic, String rawData) {
        //设备发送来的日志什么都不需要处理
    }
}
