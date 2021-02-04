package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductListDto implements BaseDto {
    Long id;

    String name;

    String description;

    String sn;

    Integer onlineCount;

    Integer deviceCount;
}
