package com.dj.iotlite.api.dto;

import com.dj.iotlite.entity.Base;
import lombok.Data;

@Data
public class UserListDto implements BaseDto {
    String name;
    String uuid;
}
