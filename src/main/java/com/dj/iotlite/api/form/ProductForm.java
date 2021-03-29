package com.dj.iotlite.api.form;

import com.dj.iotlite.enums.AccessTypeEnum;
import com.dj.iotlite.enums.DeviceCertEnum;
import com.dj.iotlite.enums.ProductDiscoverEnum;
import lombok.Data;

@Data
public class ProductForm {
    String name;
    String description;
    Integer type;
    Long adapterId;
    String model;
    String icon;
    AccessTypeEnum access;
    ProductDiscoverEnum discover;
    DeviceCertEnum cert;
}
