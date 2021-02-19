package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductListDto implements BaseDto {
    Long id;

    String name;

    String description;

    String sn;

    Object tags;

    Integer onlineCount;

    Long deviceCount;

    String ver;
}
