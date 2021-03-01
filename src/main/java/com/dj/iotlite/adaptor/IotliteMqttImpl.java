package com.dj.iotlite.adaptor;

import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.service.AdaptorService;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@Data
public class IotliteMqttImpl implements Adaptor {

    String name="IOTLITE_MQTT";

    @Autowired
    AdaptorService adaptorService;

    @Autowired
    MqttClient mqttClient;

    @Override
    public void install() {
        adaptorService.install(getName(), this.getClass().getSimpleName(), new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }

    @Override
    public void publish(Product product, Device device, String topic, String data) throws Exception {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(data.getBytes(StandardCharsets.UTF_8));
        mqttClient.publish(topic, mqttMessage);
    }
}
