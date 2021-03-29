package com.dj.iotlite.api.form;

import com.dj.iotlite.api.dto.DeviceListDto;
import com.dj.iotlite.api.dto.ProductDto;
import com.dj.iotlite.enums.AccessTypeEnum;
import com.dj.iotlite.enums.RegTypeEnum;
import lombok.Data;

@Data
public class DeviceSaveForm extends DeviceListDto {
    Long id;

    String productSn;
    String version;

    String name;
    String description;
    /**
     * 设备SN
     */
    String sn;
    Object spec;
    ProductDto product;
    Integer count;
    String deviceGroup;
    Long proxyId;
    String hdVersion;
    RegTypeEnum regType;
    AccessTypeEnum access;
}
