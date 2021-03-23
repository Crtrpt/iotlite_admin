package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceRemoveForm {
    Long id;
    String productSn;
    String deviceSn;
}
