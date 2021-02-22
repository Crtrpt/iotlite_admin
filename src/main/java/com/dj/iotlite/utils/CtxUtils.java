package com.dj.iotlite.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class CtxUtils {
    public static ConfigurableApplicationContext applicationContext;

    public static <T> T getBean(Class<T> c){

        return applicationContext.getBean(c);
    }

}
