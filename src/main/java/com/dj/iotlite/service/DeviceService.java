package com.dj.iotlite.service;

import com.dj.iotlite.entity.device.DeviceRepository;
import com.dj.iotlite.entity.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DeviceRepository deviceRepository;


    public Object queryDevice(String uuid) {
        return null;
    }

    public Object getDeviceList() {
        return null;
    }

    public Object removeDevice(String uuid) {
        return null;
    }

    public Object saveDevice() {
        return null;
    }

    public Object getProductList() {
        return null;
    }

    public Object removeProduct(String uuid) {
        return null;
    }

    public Object saveProduct() {
        return null;
    }

    public Object queryProduct(String uuid) {
        return null;
    }
}
