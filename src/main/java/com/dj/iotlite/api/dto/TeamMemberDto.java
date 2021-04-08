package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class TeamMemberDto extends TeamMemberListDto {
    Long id;
    String sn;
    String name;
    String description;
}
