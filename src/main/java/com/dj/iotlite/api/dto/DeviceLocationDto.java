package com.dj.iotlite.api.dto;

import com.dj.iotlite.serialize.LongToDateSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.lettuce.core.GeoCoordinates;
import lombok.Data;

import java.util.List;

@Data
public class DeviceLocationDto implements BaseDto {
    DeviceDto device;
    List<Object> nearby;

    Object location;

}
