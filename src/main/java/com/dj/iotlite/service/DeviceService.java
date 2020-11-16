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


}
