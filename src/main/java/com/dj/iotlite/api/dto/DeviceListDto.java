package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class DeviceListDto implements BaseDto{
    Long id;
    String name;
    ProductDto product=new ProductDto();
    String uuid;
}
