package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class AlarmListDto {
    String name;
    DeviceDto device;
    UserDto user;
    OrganizationDto organization;
    TaskDto task;
    String uuid;
}
