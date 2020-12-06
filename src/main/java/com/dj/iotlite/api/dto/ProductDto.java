package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductDto {
    String name;
    String description;
    Object spec;
}
