package com.dj.iotlite.service;

import com.dj.iotlite.entity.repo.DeviceGroupRepository;
import com.dj.iotlite.function.MemoryState;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class GroupInstanceImpl implements GroupInstance {

    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    public static HashMap<String, Script> groupScriptMapping=new HashMap<>();

    @Override
    @Async
    public void fire(String groupName, String productSn, String deviceSn, String eventName, Object payload) {
        //执行编排任务
        Script s=groupScriptMapping.get(groupName);
        if(s==null){
            deviceGroupRepository.findFirstByName(groupName).ifPresent(g->{
                GroovyShell gs = new GroovyShell();
                Script script= gs.parse(g.getSpec());
                script.setProperty("state",new MemoryState());
                groupScriptMapping.put(groupName,script);
            });
            s=groupScriptMapping.get(groupName);
        }
        s.run();

    }

    @Override
    @Async
    public void observed(String groupName, String productSn, String deviceSn, String name, Object value) {
        //执行编排任务
        log.info("设备设备属性发生变化");
    }
}
