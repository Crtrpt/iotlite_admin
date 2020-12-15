package com.dj.iotlite.entity.gateway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatewayTypeRepository extends CrudRepository<GatewayType, Long>, JpaSpecificationExecutor<GatewayType>, JpaRepository<GatewayType, Long> {

}
