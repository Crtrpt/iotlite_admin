package com.dj.iotlite.l2cache;

import com.dj.iotlite.entity.product.Product;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 产品缓存
 */
public class ProductCache {

    Cache<String, Product> cache = Caffeine.newBuilder().build();

    Product get(String sn) {
        return cache.getIfPresent(sn);
    }

    void put(String sn, Product product) {
        cache.put(sn, product);
    }

    void del(String sn) {
        cache.invalidate(sn);
    }
}
