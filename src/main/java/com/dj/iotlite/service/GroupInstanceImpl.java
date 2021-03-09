package com.dj.iotlite.service;

import com.dj.iotlite.entity.repo.DeviceGroupRepository;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class GroupInstanceImpl implements GroupInstance {

    @Autowired
    DeviceGroupRepository deviceGroupRepository;


    //TODO 优化 优化
    @Override
    @Async
    public void fire(String groupName, String productSn, String deviceSn, String eventName, Object payload) {
        //执行编排任务
        log.info("组内设备发生事件"+eventName +  groupName);
        System.out.println(groupName);
        deviceGroupRepository.findFirstByName(groupName).ifPresent(g->{
            System.out.println("执行编排"+g.getSpec());
            if(!ObjectUtils.isEmpty(g.getSpec())){
                GroovyShell gs = new GroovyShell();
                gs.setVariable("name", groupName);
                gs.evaluate(g.getSpec());
            }
        });

    }

    @Override
    @Async
    public void observed(String groupName, String productSn, String deviceSn, String name, Object value) {
        //执行编排任务
        log.info("设备设备属性发生变化");
    }
}
