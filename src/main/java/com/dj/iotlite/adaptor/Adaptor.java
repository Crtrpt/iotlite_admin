package com.dj.iotlite.adaptor;

import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Product;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Optional;

public interface Adaptor {

    default String getName() {
        return "";
    }

    default String getDesc() {
        return "";
    }

    void install() ;

    default boolean disable() {
        return true;
    }

    void uninstall();

    default void publish(Optional<Device> proxy, Product product, Device device, String topic, String data) throws Exception {
        throw new Exception("未实现");
    }
}
