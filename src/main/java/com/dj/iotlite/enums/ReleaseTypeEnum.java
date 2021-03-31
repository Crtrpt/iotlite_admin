package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum ReleaseTypeEnum {
    /**
     * 正式版
     */
    GA,
    /**
     * 测试版
     */
    Beta,
    /**
     * 开发板
     */
    Alpha,
}
