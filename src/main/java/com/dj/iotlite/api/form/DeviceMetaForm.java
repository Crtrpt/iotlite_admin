package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceMetaForm {
    String productSn;
    String deviceSn;
    String key;
    Object value;
}
