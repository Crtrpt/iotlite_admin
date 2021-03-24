package com.dj.iotlite.datapush;


import com.dj.iotlite.utils.CtxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Primary
@Service
@Slf4j
public class PrimaryDataPush implements DataPush {

    //TODO 使用二级缓存进行优化
    public void productPush(String productSn, String rawData) throws Exception {
        var key = "push_product:" + productSn;
        HashMap config = (HashMap) CtxUtils.redis.hgetall("push_product:" + productSn);
        config.put("key", key);
        CtxUtils.push.Publish(config, rawData);
    }

    public void devicePush(String productSn, String deviceSn, String rawData) throws Exception {
        HashMap config = (HashMap) CtxUtils.redis.hgetall("push_device:" + productSn + ":" + deviceSn);
        CtxUtils.push.Publish(config, rawData);
    }

    public void groupPush(String group, String rawData) throws Exception {
        HashMap config = (HashMap) CtxUtils.redis.hgetall("push_group:" + group);
        CtxUtils.push.Publish(config, rawData);
    }

    @Override
    public void Publish(Map<String, Object> config, Object payload) throws Exception {
        var impl=   (String)config.get("protocol");
        switch (impl){
            case "http":
                CtxUtils.http.Publish(config,payload);
                break;
            case "mqtt":
                CtxUtils.mqtt.Publish(config,payload);
                break;
            default:
                log.info("不存在的推送");

        }
    }
}
