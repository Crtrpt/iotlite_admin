package com.dj.iotlite.listener;


import com.dj.iotlite.datapush.DataPush;
import com.dj.iotlite.datapush.PrimaryDataPush;
import com.dj.iotlite.utils.CtxUtils;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        CtxUtils.applicationContext=applicationReadyEvent.getApplicationContext();

        CtxUtils.redis=CtxUtils.getBean(RedisCommands.class);


        CtxUtils.push=CtxUtils.getBean(PrimaryDataPush.class);

        System.out.println(CtxUtils.applicationContext.getBean("httpDataPushImpl", DataPush.class));
    }
}
