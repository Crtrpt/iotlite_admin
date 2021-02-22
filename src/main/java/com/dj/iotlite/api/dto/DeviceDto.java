package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.DeviceCertEnum;
import com.dj.iotlite.enums.ProductDiscoverEnum;
import lombok.Data;

@Data
public class DeviceDto extends DeviceListDto {
    Long id;
    String name;
    Long productId;
    String description;
    String sn;
    String uuid;
    Object spec;
    Object tags;
    ProductDto product;
    DeviceCertEnum deviceCert;
    ProductDiscoverEnum discover;
    String deviceKey;
    Object meta;
}
