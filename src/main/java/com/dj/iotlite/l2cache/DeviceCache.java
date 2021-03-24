package com.dj.iotlite.l2cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 设备直接使用 内存存储
 */
public class DeviceCache {

    Cache<String, DeviceCache> cache = Caffeine.newBuilder().build();

    DeviceCache get(String sn) {
        return cache.getIfPresent(sn);
    }

    void put(String sn, DeviceCache product) {
        cache.put(sn, product);
    }

    void del(String sn) {
        cache.invalidate(sn);
    }
}
