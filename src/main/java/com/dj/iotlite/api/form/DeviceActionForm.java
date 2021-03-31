package com.dj.iotlite.api.form;

import com.dj.iotlite.enums.SideTypeEnum;
import lombok.Data;

@Data
public class DeviceActionForm {
    String productSn;
    String deviceSn;
    String name;
    SideTypeEnum side;
}
