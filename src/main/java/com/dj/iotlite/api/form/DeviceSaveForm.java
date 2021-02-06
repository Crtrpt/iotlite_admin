package com.dj.iotlite.api.form;

import com.dj.iotlite.api.dto.DeviceListDto;
import com.dj.iotlite.api.dto.ProductDto;
import lombok.Data;

@Data
public class DeviceSaveForm extends DeviceListDto {
    Long id;
    String name;
    Long productId;
    String description;
    String sn;
    String uuid;
    Object spec;
    ProductDto product;
    Integer count;
}
