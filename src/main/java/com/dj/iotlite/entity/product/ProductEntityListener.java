package com.dj.iotlite.entity.product;

import com.dj.iotlite.entity.device.Device;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class ProductEntityListener {
    @PostPersist
    void persist(Device device){
        //增加
    }

    @PostRemove
    void remove(Device device){
        //删除
    }
}
