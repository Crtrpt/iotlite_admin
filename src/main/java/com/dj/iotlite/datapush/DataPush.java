package com.dj.iotlite.datapush;


import java.util.Map;

public interface DataPush {
    void Publish(Map<String,Object> config, Object payload) throws Exception;
}
