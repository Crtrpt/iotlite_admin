package com.dj.iotlite.datapush;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HttpDataPushImplTest {

    @Test
    void publish() throws Exception {
        var config=new HashMap<String,Object>();
        config.put("url","http://baidu.com");
        var push=new HttpDataPushImpl();
        push.Publish(config,"111");
        push.Publish(config,"222");
        Thread.sleep(10*1000);
    }
}