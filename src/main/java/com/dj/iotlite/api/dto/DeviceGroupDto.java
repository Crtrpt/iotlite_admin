package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.DeviceCertEnum;
import com.dj.iotlite.enums.ProductDiscoverEnum;
import lombok.Data;

@Data
public class DeviceGroupDto extends DeviceGroupListDto {
    String fence;
}
