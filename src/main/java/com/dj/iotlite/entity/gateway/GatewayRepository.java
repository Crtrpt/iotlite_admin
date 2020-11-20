package com.dj.iotlite.entity.gateway;


import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayRepository extends CrudRepository<Gateway, Long>, JpaSpecificationExecutor<Gateway>, JpaRepository<Gateway, Long> {

}
