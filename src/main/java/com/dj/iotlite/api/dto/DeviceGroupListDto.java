package com.dj.iotlite.api.dto;

import com.dj.iotlite.serialize.LongToDateSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class DeviceGroupListDto implements BaseDto {
    Long id;
    String name;
    String description;
    @JsonSerialize(using = LongToDateSerialize.class)
    Long createdAt;
}
