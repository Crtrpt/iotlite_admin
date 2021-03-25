package com.dj.iotlite.utils;

import com.dj.iotlite.datapush.DataPush;
import com.dj.iotlite.datapush.HttpDataPushImpl;
import com.dj.iotlite.datapush.MqttDataPushImpl;
import com.dj.iotlite.datapush.PrimaryDataPush;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.context.ConfigurableApplicationContext;

public class CtxUtils {
    public static ConfigurableApplicationContext applicationContext;


    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }

    public static Object getBean(String c){
        return applicationContext.getBean(c);
    }

    public static RedisCommands<String,String> redis;

    public static PrimaryDataPush push;
    public static HttpDataPushImpl http;
    public static MqttDataPushImpl mqtt;
}
