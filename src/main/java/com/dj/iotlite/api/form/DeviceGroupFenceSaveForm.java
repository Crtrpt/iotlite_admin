package com.dj.iotlite.api.form;

import com.dj.iotlite.api.dto.DeviceListDto;
import lombok.Data;

@Data
public class DeviceGroupFenceSaveForm extends DeviceListDto {
    Long id;
    String fence;
}
