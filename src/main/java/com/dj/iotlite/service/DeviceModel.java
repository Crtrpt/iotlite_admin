package com.dj.iotlite.service;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Map;

public interface DeviceModel {
    /**
     * 设备设备模型
     * @param productSn
     */
    void setProductSn(String productSn);

    /**
     * 设置设备的属性
     * @param property
     */
    void setProperty(String productSn,String deviceSn,String property,Object value,String desc) ;

    /**
     * 设备设备的多个属性
     * @param propertys
     */
    void setPropertys(String productSn,String deviceSn,Map<String,Object> propertys,String desc);
}
