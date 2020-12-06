package com.dj.iotlite.event;

import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangeDevice extends ApplicationEvent {
    Action action;
    Device device;

    public ChangeDevice(Object source, Device device, Action action) {
        super(source);
        this.device = device;
        this.action = action;
    }

    public enum Action {
        ADD,
        REMOVE,
        CREATE,
    }
}