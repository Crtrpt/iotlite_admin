package com.dj.iotlite.listener;

import com.dj.iotlite.entity.repo.ProductRepository;
import com.dj.iotlite.entity.repo.ProductVersionRepository;
import com.dj.iotlite.event.DeviceAdd;
import com.dj.iotlite.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Data
@Component
public class DeviceAddListener implements ApplicationListener<DeviceAdd> {

    @Autowired
    DeviceService deviceService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Override
    public void onApplicationEvent(DeviceAdd event) {

        //更新product的 数量

        //更新product版本的数量
    }
}