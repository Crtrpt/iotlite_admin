package com.dj.iotlite.entity.device;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepository extends CrudRepository<DeviceGroup, Long>, JpaSpecificationExecutor<DeviceGroup>, JpaRepository<DeviceGroup, Long> {

}
