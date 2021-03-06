package com.dj.iotlite.adaptor.IotliteMqttAdaptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Date;


@Slf4j
@Data
public class PushCallback implements MqttCallbackExtended {

    MqttClient client;

    public PushCallback(MqttClient client) {
        this.client = client;
    }

    public void connectionLost(Throwable cause) {
        cause.printStackTrace();
        log.info("连接断开" + cause.toString() + new Date());

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.info("PushCallback收到消息" + topic + "**" + message.toString());

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("投递成功");
    }

    public void connectComplete(boolean reconnect, String serverURI) {

        log.info(reconnect ? "重新连接成功" : "连接成功" + serverURI);
        try {
            String topic="/default/#";
            client.subscribe(topic, 0, new MessageCallback());
            /**
             * 设备自注册
             */
            String registerTopic="/register";
            client.subscribe(registerTopic, 0, new RegisterTopicCallback());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
