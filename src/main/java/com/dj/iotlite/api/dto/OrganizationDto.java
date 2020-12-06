package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class OrganizationDto {
    String name;
    String uuid;
    String description;
    String childrenNum;
}
