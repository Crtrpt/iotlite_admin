package com.dj.iotlite.mqtt;


import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.utils.CtxUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class MessageCallback implements IMqttMessageListener {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        log.info("收到数据"+new String(msg.getPayload(), "UTF-8"));
        try {
            CtxUtils.getBean(DeviceInstance.class).deviceResponse(topic,msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
