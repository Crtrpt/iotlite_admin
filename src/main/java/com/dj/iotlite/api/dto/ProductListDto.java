package com.dj.iotlite.api.dto;

import lombok.Data;

@Data
public class ProductListDto {
    /**
     * 产品名称
     */
    String name;
    /**
     * 产品uuid
     */
    String Uuid;
    /**
     * 在线设备数量
     */
    Integer online;
    /**
     * 设备数量
     */
    Integer deviceCount;
}
