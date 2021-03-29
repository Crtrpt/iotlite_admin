package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.*;
import com.dj.iotlite.serialize.StringMaskSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductDto extends ProductListDto {
    String name;
    String description;
    Object spec;
    String sn;
    Object tags;
    ProductDiscoverEnum discover;

    WorkTypeEnum workTypeEnum;

    DeviceCertEnum deviceCert;

    @JsonSerialize(using = StringMaskSerialize.class)
    String secKey;

    /**
     * 推送hook
     */
    Object hook;
    /**
     * 设备使用的解释器
     */
    InterpreterTypeEnum interpreter;

    /**
     * 默认所有人可见
     */
    AccessTypeEnum access;

    /**
     * 产品所有者
     */
    Long owner;

    UpdateStrategyEnum updateStrategy;
}
