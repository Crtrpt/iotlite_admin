package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.DirectionEnum;
import com.dj.iotlite.serialize.LongToDateSerialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class DeviceLogDto implements BaseDto{

    String source;

    String target;
    /**
     *  data  direction
     */
    DirectionEnum direction= DirectionEnum.UP;
    /**
     * description
     */
    String description;
    /**
     * raw data
     */
    String rawData;

    @JsonSerialize(using = LongToDateSerialize.class)
    Long createdAt;
}
