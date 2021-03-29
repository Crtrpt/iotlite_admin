package com.dj.iotlite.entity.device;

import com.dj.iotlite.utils.UUID;
import org.springframework.util.ObjectUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;

public class DeviceEntityListener {

    @PrePersist
    void prePersist(Device device){
        //增加 生成uuid
        if(ObjectUtils.isEmpty(device.getDeviceKey())){
            device.setDeviceKey(UUID.getUUID());
        }
    }

    @PostPersist
    void persist(Device device){
        //增加
    }

    @PostRemove
    void remove(Device device){
        //删除
    }
}
