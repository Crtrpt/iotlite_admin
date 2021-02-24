package com.dj.iotlite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroupInstanceImpl implements GroupInstance {

    @Override
    @Async
    public void fire(String groupName, String productSn, String deviceSn, String eventName, Object payload) {
        //执行编排任务
        log.info("组内设备发生事件"+eventName);
    }

    @Override
    @Async
    public void observed(String groupName, String productSn, String deviceSn, String name, Object value) {
        //执行编排任务
        log.info("设备设备属性发生变化");
    }
}
