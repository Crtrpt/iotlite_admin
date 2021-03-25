package com.dj.iotlite.listener;


import com.dj.iotlite.datapush.HttpDataPushImpl;
import com.dj.iotlite.datapush.MqttDataPushImpl;
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

        CtxUtils.redis=CtxUtils.applicationContext.getBean(RedisCommands.class);


        CtxUtils.push=CtxUtils.applicationContext.getBean(PrimaryDataPush.class);
//        CtxUtils.http=CtxUtils.applicationContext.getBean(HttpDataPushImpl.class);
//        CtxUtils.mqtt=CtxUtils.applicationContext.getBean(MqttDataPushImpl.class);
    }
}
