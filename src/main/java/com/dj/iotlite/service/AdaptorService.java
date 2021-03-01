package com.dj.iotlite.service;

import java.util.HashMap;

public interface AdaptorService {
    void  install(String iotlite_mqtt, String string, HashMap<String,Object> meta);
    void  unInstall(String name);
}
