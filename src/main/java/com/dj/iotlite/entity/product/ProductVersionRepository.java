package com.dj.iotlite.entity.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVersionRepository extends
        CrudRepository<ProductVersion, Long>,
        JpaSpecificationExecutor<ProductVersion>,
        JpaRepository<ProductVersion, Long> {
    Optional<ProductVersion> findFirstBySnAndVersion(String productSn,String version);
}
