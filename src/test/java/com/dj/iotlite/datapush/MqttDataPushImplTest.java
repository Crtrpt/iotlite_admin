package com.dj.iotlite.datapush;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MqttDataPushImplTest {

    @Test
    void publish() throws Exception {
        var mqttpush=new MqttDataPushImpl();
        var config=new HashMap<String,Object>();
        config.put("broker","tcp://broker.emqx.io:1883");
        config.put("topic","/test/test");
        config.put("clientId","test");
        mqttpush.Publish(config,"test");
        Thread.sleep(10*1000);

    }
}