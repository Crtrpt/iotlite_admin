package com.dj.iotlite.plugin;

public interface Gateway {
    /**
     * 创建产品
     */
    default void createProduct(Object data) {

    }

    /**
     * 删除产品
     * @param data
     */
    default void removeProduct(Object data) {

    }

    /**
     * 查询产品
     * @param data
     */
    default void queryProduct(Object data) {

    }

    /**
     * 更新产品
     * @param data
     */
    default void updateProduct(Object data) {

    }

    /**
     * 创建设备
     * @param data
     */
    default void createDevice(Object data) {

    }

    /**
     * 删除设备
     * @param data
     */
    default void removeDevice(Object data) {

    }


    /**
     * 查询设备
     * @param data
     */
    default void queryDevice(Object data) {

    }

    /**
     * 更新设备
     * @param data
     */
    default void updateDevice(Object data) {

    }

    ;
}
