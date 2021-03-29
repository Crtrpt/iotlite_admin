package com.dj.iotlite.api.form;

import com.dj.iotlite.enums.AccessTypeEnum;
import lombok.Data;

@Data
public class DeviceSaveAccessForm {
    String sn;
    String productSn;
    AccessTypeEnum access;
}
