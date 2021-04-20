package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.ReleaseTypeEnum;
import lombok.Data;

@Data
public class ProductListDto implements BaseDto {
    Long id;

    String name;

    String description;

    String sn;

    String icon;

    Object tags;

    Integer onlineCount;

    Long deviceCount;

    String version;

    ReleaseTypeEnum releaseType;
}
