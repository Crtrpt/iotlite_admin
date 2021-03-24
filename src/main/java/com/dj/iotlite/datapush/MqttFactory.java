package com.dj.iotlite.datapush;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Service
@Slf4j
public class MqttFactory extends BaseKeyedPooledObjectFactory<Map<String, Object>, IMqttToken> {

    @Override
    public IMqttToken create(Map<String, Object> cfg) throws Exception {
        var client=new MqttClient( (String) cfg.get("broker"), (String) cfg.get("clientId"), new MemoryPersistence());
        var option = new MqttConnectOptions();
        option.setCleanSession(false);
        option.setAutomaticReconnect(true);

        if(ObjectUtils.isEmpty(cfg.get("anonymous")) || (Boolean) cfg.get("anonymous")==true){
            /**
             * 匿名访问
             */
        }else {
            /**
             * 账户密码访问
             */
            option.setUserName((String) cfg.get("username"));
            option.setPassword(((String) cfg.get("password")).toCharArray());
        }
        return client.connectWithResult(option);
    }

    @Override
    public PooledObject<IMqttToken> wrap(IMqttToken client) {
        return new DefaultPooledObject<>(client);
    }
}
