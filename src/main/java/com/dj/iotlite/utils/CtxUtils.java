package com.dj.iotlite.utils;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

public class CtxUtils {
    public static ConfigurableApplicationContext applicationContext;

    public static <T> T getBean(Class<T> c){

        return applicationContext.getBean(c);
    }

    public static Object getBean(String c){
        return applicationContext.getBean(c);
    }

}
