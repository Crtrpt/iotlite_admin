package com.dj.iotlite.api.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class DeviceGroupDto extends DeviceGroupListDto {
    String fence;
    Object spec;
    Object state;
}
