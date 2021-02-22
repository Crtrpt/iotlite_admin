package com.dj.iotlite.listener;

import com.dj.iotlite.event.ChangeProduct;
import com.dj.iotlite.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Data
@Component
public class ProductListener implements ApplicationListener<ChangeProduct> {

    @Autowired
    DeviceService deviceService;

    @Override
    public void onApplicationEvent(ChangeProduct changeProduct) {
        log.info("user modify");
    }
}