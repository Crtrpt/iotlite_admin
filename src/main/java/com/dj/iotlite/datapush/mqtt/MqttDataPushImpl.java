package com.dj.iotlite.datapush.mqtt;

import com.dj.iotlite.datapush.DataPush;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MqttDataPushImpl implements DataPush {

    KeyedObjectPool<Map<String, Object>, IMqttToken> mqttFactory = new GenericKeyedObjectPool<>(new MqttFactory());


    static HashMap<String, Object> defaultConfig = new HashMap<>();

    static {
        defaultConfig.put("anonymous", true);
        defaultConfig.put("qos", 0);
        defaultConfig.put("retained", false);
    }

    /**
     * TODO 池化配置提高性能
     *
     * @param config
     * @param payload
     * @throws MqttException
     */
    @Override
    @Async
    public void Publish(Map<String, Object> config, Object payload) throws Exception {
        var cfg = (HashMap<String, Object>) defaultConfig.clone();
        cfg.putAll(config);
        var token = mqttFactory.borrowObject(cfg);
        if (token.isComplete()) {
            var msg = new MqttMessage();
            msg.setQos((int) cfg.get("qos"));
            msg.setRetained((boolean) cfg.get("retained"));
            msg.setPayload(((String) payload).getBytes(StandardCharsets.UTF_8));

            System.out.println((String) cfg.get("topic"));
            token.getClient().publish((String) cfg.get("topic"), msg);
        }
        mqttFactory.returnObject(cfg, token);
    }
}
