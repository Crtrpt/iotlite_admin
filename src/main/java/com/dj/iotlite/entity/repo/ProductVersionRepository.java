package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductVersion;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames={"product_version"})

public interface ProductVersionRepository extends
        CrudRepository<ProductVersion, Long>,
        JpaSpecificationExecutor<ProductVersion>,
        JpaRepository<ProductVersion, Long> {
    /**
     * 缓存指定版本的产品
     * @param productSn
     * @param version
     * @return
     */
    @Cacheable(key="#productSn+'_'+version")
    Optional<ProductVersion> findFirstBySnAndVersion(String productSn,String version);

    @Override
    @Cacheable(key = "#id")
    Optional<ProductVersion> findById(Long id);

    @Override
    @CachePut(key="#product.id")
    ProductVersion save(ProductVersion product);

    @Override
    @CacheEvict(key="#product.id")
    void delete(ProductVersion product);
}
