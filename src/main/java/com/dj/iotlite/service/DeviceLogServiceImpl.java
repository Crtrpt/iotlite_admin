package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.entity.repo.DeviceLogRepository;
import com.dj.iotlite.enums.DirectionEnum;
import io.lettuce.core.api.sync.RedisCommands;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceLogServiceImpl implements DeviceLogService {

    @Autowired
    DeviceLogRepository deviceLogRepository;

    @Autowired
    RedisCommands<String, String> redisCommands;

    @Override
    public void Log(String deviceSn,String productSn,DirectionEnum direction, String source, String target, String desc, String data,LogLevel level) {

        String member = String.format(RedisKey.DEVICE, productSn, deviceSn);

        redisCommands.hset(member, "lastAt", String.valueOf(System.currentTimeMillis()) );


        log.info("{} {} {} {} {} {}", System.currentTimeMillis(), direction, source, target, desc, data);
        var log = new DeviceLog();
        log.setCreatedAt(System.currentTimeMillis());
        log.setDirection(direction);
        log.setSource(source);
        log.setProductSn(productSn);
        log.setDeviceSn(deviceSn);
        log.setLevel(LogLevel.TRACE);
        log.setTarget(target);
        log.setDescription(desc);
        log.setRawData(data);
        deviceLogRepository.save(log);
    }
}
