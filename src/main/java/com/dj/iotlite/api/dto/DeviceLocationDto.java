package com.dj.iotlite.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeviceLocationDto implements BaseDto {
    DeviceDto device;
    List<Object> nearby;

    Object location;

}
