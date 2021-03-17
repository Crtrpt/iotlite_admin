package com.dj.iotlite.listener;


import com.dj.iotlite.utils.CtxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AppDownListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        CtxUtils.applicationContext=applicationReadyEvent.getApplicationContext();
    }
}
