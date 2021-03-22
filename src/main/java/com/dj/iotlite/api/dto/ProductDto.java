package com.dj.iotlite.api.dto;

import com.dj.iotlite.enums.DeviceCertEnum;
import com.dj.iotlite.enums.ProductDiscoverEnum;
import com.dj.iotlite.enums.WorkTypeEnum;
import com.dj.iotlite.serialize.StringMaskSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

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
}
