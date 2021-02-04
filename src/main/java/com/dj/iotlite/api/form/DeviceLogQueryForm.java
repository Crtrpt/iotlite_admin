package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceLogQueryForm extends PageForm {
    String deviceSn;
    String productSn;
}
