package com.dj.iotlite.service;

import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.entity.repo.DeviceLogRepository;
import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.utils.JsonUtils;
import com.dj.iotlite.utils.StringUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class DeviceLogServiceImpl implements DeviceLogService {

    @Autowired
    DeviceLogRepository deviceLogRepository;

    @Override
    public void Log(String deviceSn,String productSn,DirectionEnum direction, String source, String target, String desc, String data) {
        log.info("{} {} {} {} {} {}", System.currentTimeMillis(), direction, source, target, desc, data);
        var log = new DeviceLog();
        log.setCreatedAt(System.currentTimeMillis());
        log.setDirection(direction);
        log.setSource(source);
        log.setProductSn(productSn);
        log.setDeviceSn(deviceSn);
        log.setTarget(target);
        log.setDescription(desc);
        log.setRawData(data);
        deviceLogRepository.save(log);
    }
}
