package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum DeviceCertEnum {
    /**
     * 手动添加
     */
    none,
    /**
     * 设备自动注册
     */
    product,

    device,
}