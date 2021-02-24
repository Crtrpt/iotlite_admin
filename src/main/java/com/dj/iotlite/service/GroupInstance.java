package com.dj.iotlite.service;



public interface GroupInstance {
    public void fire(String groupName, String productSn,String deviceSn,String eventName,Object payload);
    public void observed(String groupName,String productSn,String deviceSn,String name,Object value);
}
