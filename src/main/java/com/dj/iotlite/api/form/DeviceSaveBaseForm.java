package com.dj.iotlite.api.form;

import com.dj.iotlite.enums.AccessTypeEnum;
import lombok.Data;

@Data
public class DeviceSaveBaseForm {
    String sn;
    String productSn;
    String name;
    String description;
}
