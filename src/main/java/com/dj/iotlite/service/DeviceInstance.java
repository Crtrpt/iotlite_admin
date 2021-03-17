package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.repo.*;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.utils.CtxUtils;
import com.dj.iotlite.utils.JsonUtils;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    AdapterRepository adapterRepository;

    @Autowired
    PushService pushService;

    @Autowired
    DeviceLogService deviceLogService;

    @Override
    public void setProductSn(String productSn) {

    }

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

        deviceLogService.Log(deviceSn, productSn, DirectionEnum.Down, "admin", topic, desc, JsonUtils.toJson(propertys));
        try {
            log.info("topic {}  data: {}", topic, data);
            if (ObjectUtils.isEmpty(product.getAdapterId())) {
                throw new BusinessException("未找到设备 适配器");
            } else {
                adapterRepository.findById(product.getAdapterId()).ifPresent(adaptor -> {
                    try {
                        ((Adaptor) CtxUtils.getBean(adaptor.getImplClass())).publish(product, device, topic, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("下发设备信息异常");
        }
    }

    @Autowired
    RedisCommands<String, String> redisCommands;

    public void deviceResponse(String productSn, String deviceSn, String topic, String rawData) throws Exception {
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
        String name = JsonPath.read(data, "$.name");
        Object value = JsonPath.read(data, "$.value");
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);
        redisCommands.hset(member, name, String.valueOf(value));
    }

    public void deviceEventFire(String productSn, String deviceSn, String topic, String rawData) {
        String name = JsonPath.read(rawData, "$.name");
        Object payload = null;
        try {
            payload = JsonPath.read(rawData, "$.payload");
        } catch (Exception e) {
            log.info("payload is empty");
        }
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);


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

    }

    public void deviceLog(String productSn, String deviceSn, String topic, String rawData) {
        //设备发送来的日志什么都不需要处理
    }
}
