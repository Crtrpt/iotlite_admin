package com.dj.iotlite.listener;

import com.dj.iotlite.event.ChangeOrganization;
import com.dj.iotlite.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Slf4j
@Data
@Component
public class OrganizationListener implements ApplicationListener<ChangeOrganization> {

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ChangeOrganization changeOrganization) {
        log.info("");
        userService.refreshChildrenNum(changeOrganization.getOrganization());
    }
}