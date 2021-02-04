package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceLogRepository;
import com.dj.iotlite.entity.device.DeviceRepository;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.exception.BusinessException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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
        String data = gson.toJson(propertys);

        device.setVersion(device.getVersion() + 1);
        deviceRepository.save(device);
        //写入下发日志

        propertys.put("v", device.getVersion());
        MqttMessage mqttMessage = new MqttMessage();

        mqttMessage.setPayload(data.getBytes(StandardCharsets.UTF_8));
        deviceLogService.Log(deviceSn, productSn, DirectionEnum.Down, "admin", topic, desc, propertys);
        try {
            log.info("topic {}  data: {}", topic, data);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
            throw new BusinessException("下发设备信息异常");
        }
    }


}
