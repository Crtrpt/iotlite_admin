package com.dj.iotlite.datapush;


import com.dj.iotlite.utils.CtxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Primary
public class PrimaryDataPush implements DataPush {

    //TODO 使用二级缓存进行优化
    public void productPush(String productSn, String rawData) throws Exception {
        var key="hook@product@" + productSn;
        HashMap config = (HashMap)(CtxUtils.redis.hgetall(key));
        config.put("key",key);
        CtxUtils.push.Publish(config, rawData);
    }

    public void devicePush(String productSn, String deviceSn, String rawData) throws Exception {
        var key="hook@device@" + productSn + "," + deviceSn;
        HashMap config = (HashMap) CtxUtils.redis.hgetall(key);
        config.put("key",key);
        CtxUtils.push.Publish(config, rawData);
    }

    public void groupPush(String group, String rawData) throws Exception {
        var key="hook@group@" + group;
        HashMap config = (HashMap) CtxUtils.redis.hgetall("hook@group@" + group);
        config.put("key",key);
        CtxUtils.push.Publish(config, rawData);
    }

    @Override
    public void Publish(Map<String, Object> config, Object payload) throws Exception {
        var impl=   (String)config.get("protocol");
        if(ObjectUtils.isEmpty(impl)){
            return;
        }
        switch (impl){
            case "http":
                CtxUtils.applicationContext.getBean("httpDataPushImpl", DataPush.class).Publish(config,payload);
                break;
            case "mqtt":
                CtxUtils.applicationContext.getBean("mqttDataPushImpl", DataPush.class).Publish(config,payload);
                break;
            case "sse":
                CtxUtils.applicationContext.getBean("sse1DataPushImpl", DataPush.class).Publish(config,payload);
                break;
            default:
                log.info("不存在的推送");

        }
    }
}
