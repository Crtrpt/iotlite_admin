package com.dj.iotlite.api.form;

import com.dj.iotlite.enums.AccessTypeEnum;
import lombok.Data;

@Data
public class ProductSaveAccessForm {
    String sn;
    AccessTypeEnum access;
}
