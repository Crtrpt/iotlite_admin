package com.dj.iotlite.api.dto;


import lombok.Data;

import java.util.List;

@Data
public class AlarmDto {
    String name;
    DeviceDto device;
    List<UserDto> user;
    OrganizationDto organization;
}
