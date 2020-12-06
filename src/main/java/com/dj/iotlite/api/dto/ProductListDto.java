package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductListDto implements BaseDto {
    Long id;

    String name;

    String description;

    String Uuid;

    Integer onlineCount;

    Integer deviceCount;
}
