package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.ReleaseTypeEnum;
import com.dj.iotlite.serialize.LongToDateSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class DeviceListDto implements BaseDto {
    Long id;
    String name;
    ProductDto product = new ProductDto();
    String sn;
    @JsonSerialize(using = LongToDateSerialize.class)
    Long createdAt;
    Object tags;
    String version;
    String hdVersion;
    String deviceGroup;
    /**
     * 设备发行类型
     */
    ReleaseTypeEnum releaseType;
}
