package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceDto {
    Long id;
    String name;
    Long productId;
    String description;
    String sn;
    String uuid;

    ProductDto product;
}
