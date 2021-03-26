package com.dj.iotlite.adaptor.IotliteMqttAdaptor;


import com.dj.iotlite.adaptor.MessageScheduling;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.service.DeviceService;
import com.dj.iotlite.utils.CtxUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class RegisterTopicCallback implements IMqttMessageListener , MessageScheduling {

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
            var rawData = new String(msg.getPayload(), "UTF-8");
            Gson gson=new Gson();
            var regDto=gson.fromJson(rawData,RegisterDto.class);
            var device = CtxUtils.getBean(DeviceService.class);
            device.deviceRegister(regDto);
            log.info("设备自动注册");
    }
}
