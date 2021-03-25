package com.dj.iotlite.adaptor;


import com.dj.iotlite.RedisKey;
import com.dj.iotlite.config.MacroConfig;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.service.DeviceInstance;
import com.dj.iotlite.service.DeviceLogServiceImpl;
import com.dj.iotlite.service.GroupInstance;
import com.dj.iotlite.utils.CtxUtils;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;



public interface MessageScheduling {

     Logger log = LoggerFactory.getLogger(MessageScheduling.class);

     default void dispatch(String topic, byte[] msg) throws Exception {
          var rawData = new String(msg, "UTF-8");
          log.info("收到数据" + rawData);

          var seg = topic.split("/");

          var deviceSn = seg[3];
          var productSn = seg[2];

          if (MacroConfig.push) {
               try {
                    CtxUtils.push.devicePush(productSn, deviceSn, rawData);
                    CtxUtils.push.productPush(productSn, rawData);
               } catch (Exception e) {
                    e.printStackTrace();
               }
          }
          log.info("收到数据" + seg.length + "");

          String action = JsonPath.read(rawData, "$.action");
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
                              CtxUtils.redis.geoadd(key, Double.parseDouble(locationSeg[0]), Double.parseDouble(locationSeg[1]), member);
                         } catch (Exception e) {
                              e.printStackTrace();
                         }
                         break;
                    case "pass":
                         String realTopic = JsonPath.read(rawData, "$.topic");
                         String payload = JsonPath.read(rawData, "$.payload");
                         dispatch(realTopic, msg);
                    default:
                         log.error("未定义action");
               }
               //更新前端的状态
          } catch (Exception e) {
               e.printStackTrace();
          }

          //TODO 设备组编排
          var groupName = CtxUtils.redis.hget(String.format(RedisKey.DEVICE, productSn, deviceSn), "deviceGroup");
          if (!ObjectUtils.isEmpty(groupName)) {
               var groups = ((String) groupName).split(",");
               for (String g : groups) {
                    CtxUtils.getBean(GroupInstance.class).fire(g, productSn, deviceSn, action, rawData);
                    if (MacroConfig.push) {
                         CtxUtils.push.groupPush(g, rawData);
                    }
               }
          }

          CtxUtils.getBean(PushService.class).push("/device/" + productSn + "/" + deviceSn + "/", rawData);

     }
}
