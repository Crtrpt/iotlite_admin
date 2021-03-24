package com.dj.iotlite.entity.repo;




import com.dj.iotlite.entity.product.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends
        CrudRepository<Product, Long>,
        JpaSpecificationExecutor<Product>,
        JpaRepository<Product, Long> {
    Optional<Product> findFirstBySn(String productSn);

    @Override
    @Cacheable(cacheNames = "product",key = "#id")
    Optional<Product> findById(Long id);

    @Override
    @CacheEvict(cacheNames = "product",key="#product.id")
    Product save(Product product);

    @Override
    @CacheEvict(cacheNames = "product",key="#product.id")
    void delete(Product product);

}
