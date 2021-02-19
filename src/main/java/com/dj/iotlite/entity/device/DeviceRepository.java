package com.dj.iotlite.entity.device;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long>, JpaSpecificationExecutor<Device>, JpaRepository<Device, Long> {

    Page<Device> findAll(Specification spec, Pageable  page);

    Optional<Device> findFirstBySnAndProductSn(String deviceSn,String productSn);

    Long countByProductSn(String productSn);
}
