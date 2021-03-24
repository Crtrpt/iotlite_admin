package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceGroupDto extends DeviceGroupListDto {
    String fence;
    Object spec;
    Object state;
    /**
     * 推送hook
     */
    Object hook;
}
