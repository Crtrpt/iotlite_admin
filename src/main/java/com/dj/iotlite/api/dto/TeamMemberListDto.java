package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class TeamMemberListDto implements BaseDto {
    Long id;
    String name;
    String description;
    /**
     * team的sn
     */
    String sn;
}
