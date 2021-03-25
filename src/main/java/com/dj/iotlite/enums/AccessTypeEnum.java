package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum AccessTypeEnum {
    /**
     * 所有人可见
     */
    Public,
    /**
     * 仅自己可见
     */
    Private,
    /**
     * 团队可见
     */
    Team,
}
