package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceMapLocationDto implements BaseDto {
    String deviceSn;
    String productSn;
    Object location;
}
