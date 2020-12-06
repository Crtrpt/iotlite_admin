package com.dj.iotlite.utils;

public class UUID {
   public static String getUUID(){
        SnowFlakeUtils SnowFlakeUtils = new SnowFlakeUtils(1,1,1);
        return String.valueOf(SnowFlakeUtils.nextId());
    }
}
