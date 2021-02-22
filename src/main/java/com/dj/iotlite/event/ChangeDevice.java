package com.dj.iotlite.event;

import com.dj.iotlite.entity.device.Device;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ChangeDevice extends ApplicationEvent {
    Action action;
    Device device;
    Map<String,Long> groupIdsMap=new HashMap<String, Long>();

    public ChangeDevice(Object source, Device device, Action action,HashMap<String,Long> groupIdsMap) {
        super(source);
        this.device = device;
        this.action = action;
        this.groupIdsMap=groupIdsMap;
    }

    public enum Action {
        ADD,
        REMOVE,
        CREATE,
    }
}