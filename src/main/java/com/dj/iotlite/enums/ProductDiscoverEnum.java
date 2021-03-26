package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum ProductDiscoverEnum {
    /**
     * 手动注册 自动注册都支持
     */
    all,
    /**
     * 设备只支持设备手动注册
     */
    manully,
    /**
     * 设备只支持设备自动注册
     */
    auto,
}