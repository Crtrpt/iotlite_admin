package com.dj.iotlite.config;

import com.dj.iotlite.mqtt.PushCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class MqttClientConfig {

    @Value("${app.mqtt.serverURI}")
    String  serverURI;

    @Value("${app.mqtt.clientId}")
    String clientId;

    @Bean("mqttClient")
    MqttClient mqttClient() throws MqttException {
        log.info("启动mqtt客户端");
        var option = new MqttConnectOptions();
        option.setCleanSession(false);
        option.setAutomaticReconnect(true);

        MqttClient mqttClient=new MqttClient(serverURI,clientId, new MemoryPersistence());
        mqttClient.setCallback(new PushCallback(mqttClient));
        mqttClient.connect(option);
        return  mqttClient;
    }
}
