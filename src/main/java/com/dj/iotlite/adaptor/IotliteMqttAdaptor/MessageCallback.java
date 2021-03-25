package com.dj.iotlite.adaptor.IotliteMqttAdaptor;


import com.dj.iotlite.RedisKey;
import com.dj.iotlite.adaptor.MessageScheduling;
import com.dj.iotlite.config.MacroConfig;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.service.DeviceLogServiceImpl;
import com.dj.iotlite.service.GroupInstance;
import com.dj.iotlite.utils.CtxUtils;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.util.ObjectUtils;


import static java.nio.charset.StandardCharsets.UTF_8;


@Slf4j
public class MessageCallback implements IMqttMessageListener , MessageScheduling {

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {

    }
}
