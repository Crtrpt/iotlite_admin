package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class GatewayListDto implements BaseDto {
    String name;
    String uuid;
}
