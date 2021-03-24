package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.device.Device;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames={"device"})
public interface DeviceRepository extends CrudRepository<Device, Long>, JpaSpecificationExecutor<Device>, JpaRepository<Device, Long> {

    Page<Device> findAll(Specification spec, Pageable page);

    Optional<Device> findFirstBySnAndProductSn(String deviceSn, String productSn);

    List<Device> findAllByProductSn(String productSn);

    Long countByProductSn(String productSn);

    @Override
    @Cacheable()
    Optional<Device> findById(Long id);

    @Override
    @CacheEvict(cacheNames = "device", key = "#device.id")
    Device save(Device device);

    @Override
    @CacheEvict(cacheNames = "device", key = "#device.id")
    void delete(Device device);
}
