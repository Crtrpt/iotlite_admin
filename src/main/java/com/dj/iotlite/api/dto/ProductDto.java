package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductDto extends ProductListDto {
    String name;
    String description;
    Object spec;
    String sn;
    Object tags;
}
