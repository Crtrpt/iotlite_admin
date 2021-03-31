package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.*;
import lombok.Data;

import javax.persistence.Column;

@Data
public class DeviceDto extends DeviceListDto {
    Long id;
    String name;
    Long productId;
    String description;
    String sn;
    String uuid;
    Object spec;
    Object tags;
    ProductDto product;
    DeviceCertEnum deviceCert;
    ProductDiscoverEnum discover;
    String deviceKey;
    Object meta;
    /**
     * 设备快照
     */
    Object snap;
    DeviceDto proxy;
    /**
     * 推送hook
     */
    Object hook;

    /**
     * 默认所有人可见
     */
    AccessTypeEnum access;


    UpdateStrategyEnum updateStrategy;

}
