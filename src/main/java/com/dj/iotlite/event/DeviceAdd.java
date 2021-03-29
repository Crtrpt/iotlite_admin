package com.dj.iotlite.event;

import com.dj.iotlite.entity.device.Device;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DeviceAdd extends ApplicationEvent {

    Device device;

    public DeviceAdd(Object source, Device device) {
        super(source);
        this.device = device;
    }
}