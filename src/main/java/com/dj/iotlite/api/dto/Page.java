package com.dj.iotlite.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page<T extends BaseDto> {
    Integer pageSize = 1;
    Integer total = 0;
    List<T> data = new ArrayList<>();
}
