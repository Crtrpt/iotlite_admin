package com.dj.iotlite.event;

import com.dj.iotlite.entity.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangeUser extends ApplicationEvent {
    Action action;
    User user;

    public ChangeUser(Object source, User user, Action action) {
        super(source);
        this.user = user;
        this.action = action;
    }

    public enum Action {
        ADD,
        REMOVE,
        CREATE,
    }
}