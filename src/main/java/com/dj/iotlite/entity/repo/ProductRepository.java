package com.dj.iotlite.entity.repo;

import com.dj.iotlite.entity.product.Product;
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
@CacheConfig(cacheNames={"product"})
public interface ProductRepository extends
        CrudRepository<Product, Long>,
        JpaSpecificationExecutor<Product>,
        JpaRepository<Product, Long> {

    @Cacheable(key = "#productSn")
    Optional<Product> findFirstBySn(String productSn);

    @Override
    Optional<Product> findById(Long id);

    @Override
    @CachePut(key="#product.sn")
    Product save(Product product);

    @Override
    @CachePut(key="#product.sn")
    void delete(Product product);

}
