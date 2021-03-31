package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum SideTypeEnum {
    /**
     * 服务端提供
     */
    Server,
    /**
     * 设备端自带
     */
    Client,
}
