package com.dj.iotlite.event;

import com.dj.iotlite.entity.organization.Organization;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangeOrganization extends ApplicationEvent {
    Action action;
    Organization organization;
    public ChangeOrganization(Object source, Organization organization, ChangeOrganization.Action action) {
        super(source);
        this.organization=organization;
        this.action=action;
    }

    public enum Action {
        ADD,
        REMOVE,
        CREATE,
    }
}


