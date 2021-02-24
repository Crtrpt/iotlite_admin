package com.dj.iotlite.mqtt;


import com.dj.iotlite.RedisKey;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.service.DeviceLogServiceImpl;
import com.dj.iotlite.utils.CtxUtils;
import com.jayway.jsonpath.JsonPath;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;


@Slf4j
public class MessageCallback implements IMqttMessageListener {


    @Override
    public void messageArrived(String topic, MqttMessage msg) throws Exception {
        var rawData = new String(msg.getPayload(), "UTF-8");
        log.info("收到数据" + rawData);
        String action = JsonPath.read(rawData, "$.action");
        var seg = topic.split("/");
        var deviceSn = seg[3];
        var productSn = seg[2];
        //log
        CtxUtils.getBean(DeviceLogServiceImpl.class).Log(deviceSn, productSn, DirectionEnum.UP, "device", topic, action, rawData);

        var device = CtxUtils.getBean(DeviceInstance.class);
        //action
        try {
            switch (action) {
                case "response":
                    log.info("设置经纬度收到设备回复");
                    device.deviceResponse(productSn, deviceSn, topic, rawData);
                    break;
                case "property":
                    log.info("设备属性发生变化");
                    device.devicePropertyChange(productSn, deviceSn, topic, rawData);
                    break;
                // 尽量设备来处理计算的压力
                case "event":
                    log.info("设备发生事件");
                    device.deviceEventFire(productSn, deviceSn, topic, rawData);
                    break;
                case "log":
                    log.info("收到设备发送来的日志");
                    device.deviceLog(productSn, deviceSn, topic, rawData);
                    break;
                case "alarm":
                    log.info("设备发生报警");
                    device.deviceAlarm(productSn, deviceSn, topic, rawData);
                    break;
                case "location":
                    String location = JsonPath.read(rawData, "$.position");
                    var locationSeg = location.split(",");
                    String key = String.format(RedisKey.DeviceLOCATION, "default");
                    String member = String.format(RedisKey.DeviceLOCATION_MEMBER, productSn, deviceSn);
                    log.info("设置经纬度  {} {} {} {} ", key, member, Double.parseDouble(locationSeg[0]), Double.parseDouble(locationSeg[1]));
                    //更新设备自身的经纬度
                    try {
                        CtxUtils.getBean(RedisCommands.class).geoadd(key, Double.parseDouble(locationSeg[0]), Double.parseDouble(locationSeg[1]), member);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    log.error("未定义action");
            }
            //更新前端的状态
        } catch (Exception e) {
            e.printStackTrace();
        }
        CtxUtils.getBean(PushService.class).push("/device/" + productSn + "/" + deviceSn + "/", rawData);
    }
}
