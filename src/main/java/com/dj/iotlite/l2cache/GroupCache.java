package com.dj.iotlite.l2cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 分组场景缓存
 */
public class GroupCache {

    Cache<String, GroupCache> cache = Caffeine.newBuilder().build();

    GroupCache get(String sn) {
        return cache.getIfPresent(sn);
    }

    void put(String sn, GroupCache product) {
        cache.put(sn, product);
    }

    void del(String sn) {
        cache.invalidate(sn);
    }
}
