package com.dj.iotlite.api.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductVersionListDto implements BaseDto {
    Long createdAt;
    String verDescription;
    Date startAt;
    String version;
    Date endAt;
    Long deviceCount;
    String minHdVersion;
}
