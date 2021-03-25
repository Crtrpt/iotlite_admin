package com.dj.iotlite.datapush;

import com.dj.iotlite.datapush.http.HttpDataPushImpl;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

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