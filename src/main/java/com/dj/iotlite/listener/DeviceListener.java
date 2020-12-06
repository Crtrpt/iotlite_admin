package com.dj.iotlite.listener;

import com.dj.iotlite.event.ChangeDevice;
import com.dj.iotlite.event.ChangeUser;
import com.dj.iotlite.service.DeviceService;
import com.dj.iotlite.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Data
@Component
public class DeviceListener implements ApplicationListener<ChangeDevice> {

    @Autowired
    DeviceService deviceService;

    @Override
    public void onApplicationEvent(ChangeDevice changeDevice) {
        log.info("device modify");

    }
}