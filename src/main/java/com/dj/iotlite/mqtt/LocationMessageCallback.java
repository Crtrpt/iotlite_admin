package com.dj.iotlite.mqtt;


import com.dj.iotlite.RedisKey;
import com.dj.iotlite.api.dto.DeviceLocationDto;
import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.utils.CtxUtils;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LocationMessageCallback implements IMqttMessageListener {



    @Autowired
    RedisCommands<String, String> redisCommands;

    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        log.info("修改设备位置");

        var location = new String(msg.getPayload(), "UTF-8");
        var locationSeg = location.split(",");
        var seg = topic.split("/");
        var deviceSn = seg[3];
        var productSn = seg[2];
        String key = String.format(RedisKey.DeviceLOCATION, "default");
        String member = String.format(RedisKey.DeviceLOCATION_MEMBER, productSn, deviceSn);

        log.info("设置经纬度  {} {} {} {} ", key, member, Double.parseDouble(locationSeg[0]), Double.parseDouble(locationSeg[1]));
        //更新设备自身的经纬度
        try {
            CtxUtils.getBean(RedisCommands.class).geoadd(key, Double.parseDouble(locationSeg[0]), Double.parseDouble(locationSeg[1]), member);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
