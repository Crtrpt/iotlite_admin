package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceListDto {
    String name;
    ProductDto product;
    String uuid;
}
