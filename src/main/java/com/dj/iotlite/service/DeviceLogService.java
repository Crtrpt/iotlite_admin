package com.dj.iotlite.service;

import com.dj.iotlite.enums.DirectionEnum;

public interface DeviceLogService {
    void Log(String deviceSn,String productSn,DirectionEnum direction,String source,String target,String desc,String data);
}
