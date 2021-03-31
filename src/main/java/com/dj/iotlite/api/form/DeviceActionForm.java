package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceActionForm {
    String productSn;
    String deviceSn;
    String name;
    SideType side;
}
