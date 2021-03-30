package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.entity.repo.ProductRepository;
import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.entity.repo.AdapterRepository;
import com.dj.iotlite.entity.repo.ProductVersionRepository;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.push.PushService;
import com.dj.iotlite.utils.CtxUtils;
import com.dj.iotlite.utils.JsonUtils;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class DeviceInstance implements DeviceModel {

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    AdapterRepository adapterRepository;

    @Autowired
    PushService pushService;

    @Autowired
    DeviceLogService deviceLogService;

    @Override
    public void setProductSn(String productSn) {

    }

    @Override
    public void setProperty(String productSn, String deviceSn, String property, Object value, String desc) {
        Map<String, Object> propertys = new HashMap<>();
        propertys.put(property, value);
        setPropertys(productSn, deviceSn, propertys, desc);
    }

    @Override
    public void setPropertys(String productSn, String deviceSn, Map<String, Object> propertys, String desc) {


        Device device = deviceRepository.findFirstBySnAndProductSn(deviceSn, productSn).orElseThrow(() -> {
            throw new BusinessException("设备序号不存在");
        });

        ProductVersion product = productVersionRepository.findFirstBySnAndVersion(device.getProductSn(),device.getVersion()).orElseThrow(() -> {
            throw new BusinessException("产品序号不存在");
        });

        Optional<Device> proxy = Optional.of(null);
        String topic = String.format(RedisKey.DeviceProperty, "default", productSn, deviceSn);

        //透传下发
        if (device.getProxyId() != null) {
            proxy = deviceRepository.findById(device.getProxyId());
            topic = String.format(RedisKey.DeviceProperty, "default", proxy.get().getProductSn(), proxy.get().getSn());
        }

        Gson gson = new Gson();


        propertys.put("v", device.getVer());

        String data = gson.toJson(propertys);

        device.setVer(device.getVer() + 1);
        deviceRepository.save(device);
        //写入下发日志

        deviceLogService.Log(deviceSn, productSn, DirectionEnum.Down, "admin", topic, desc, JsonUtils.toJson(propertys));
        try {
            log.info("topic {}  data: {}", topic, data);
            if (ObjectUtils.isEmpty(product.getAdapterId())) {
                throw new BusinessException("未找到设备 适配器");
            } else {
                Optional<Device> finalProxy = proxy;
                String finalTopic = topic;
                adapterRepository.findById(product.getAdapterId()).ifPresent(adaptor -> {
                    try {
                        ((Adaptor) CtxUtils.getBean(adaptor.getImplClass())).publish(finalProxy, product, device, finalTopic, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("下发设备信息异常");
        }
    }

    @Autowired
    RedisCommands<String, String> redisCommands;

    public void deviceResponse(String productSn, String deviceSn, String topic, String rawData) throws Exception {
        Integer v = JsonPath.read(rawData, "$.v");
        //TODO 并发问题处理
        deviceRepository.findFirstBySnAndProductSn(deviceSn, productSn).ifPresent((d) -> {
            d.setVer(v);
            deviceRepository.save(d);
        });
    }

    @Autowired
    GroupInstance groupInstance;

    public void devicePropertyChange(String productSn, String deviceSn, String topic, String data) {
        HashMap<String,Object> payload = JsonPath.read(data, "$.payload");
        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);
        payload.forEach((key,v)->{
            redisCommands.hset(member, key, String.valueOf(v));
            //设备值最后变更时间
            redisCommands.hset(member, key + ":last_at", String.valueOf(System.currentTimeMillis()));
        });
    }

    public void deviceEventFire(String productSn, String deviceSn, String topic, String rawData) {
        log.info("设备发生事件  更新");
    }

    public void deviceAlarm(String productSn, String deviceSn, String topic, String rawData) {
        log.info("设备发生告警  更新");
    }

    public void deviceLog(String productSn, String deviceSn, String topic, String rawData) {
        //设备发送来的日志什么都不需要处理
    }
}
