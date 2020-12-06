package com.dj.iotlite.api.dto;

import com.dj.iotlite.entity.Base;
import lombok.Data;

@Data
public class UserListDto implements BaseDto {
    Long id;
    String name;
    String email;
    String phone;
    String desc;
    OrganizationDto organization;
    String lastLoginIp;
    String lastLoginTime;
    Boolean isActive;
}
