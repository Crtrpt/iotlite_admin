package com.dj.iotlite.entity.device;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceUserRepository extends CrudRepository<DeviceUser, Long>, JpaSpecificationExecutor<DeviceUser>, JpaRepository<DeviceUser, Long> {

}
