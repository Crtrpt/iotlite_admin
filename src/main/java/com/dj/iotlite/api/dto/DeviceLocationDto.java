package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceLocationDto implements BaseDto {
    DeviceDto device;
    Object nearby;
    Object location;

}
