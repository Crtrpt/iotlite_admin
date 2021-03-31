package com.dj.iotlite.service;

import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.entity.adaptor.Adapter;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.ProductVersion;

import java.util.HashMap;
import java.util.Map;

public interface DeviceModel {
    /**
     * 设备设备模型
     * @param productSn
     */
    void setProductSn(String productSn);
    /**
     * 设备设备的多个属性
     * @param propertys
     */
    void setPropertys(ProductVersion product, Device device, HashMap<String, Object> propertys, String name, String ackId, Adapter adaptor) throws Exception;

    /**
     *
     * @param product
     * @param device
     * @param propertys
     * @param name
     * @param ackId
     */
    public void setControl(ProductVersion product, Device device, HashMap<String, Object> propertys, String name, String ackId,Adapter adaptor) throws Exception;
}
