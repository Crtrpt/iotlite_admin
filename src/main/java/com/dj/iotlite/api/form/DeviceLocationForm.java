package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class DeviceLocationForm {
    String productSn;
    String deviceSn;
    /**
     * 附近的设备 null 不获取附近的设备
     */
    Integer radius;
}
