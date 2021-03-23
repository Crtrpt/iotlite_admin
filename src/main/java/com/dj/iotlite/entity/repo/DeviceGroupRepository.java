package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.device.*;
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
public interface DeviceGroupRepository extends CrudRepository<DeviceGroup, Long>, JpaSpecificationExecutor<DeviceGroup>, JpaRepository<DeviceGroup, Long> {

    Optional<DeviceGroup> findFirstByName(String name);

    Page<DeviceGroup> findAll(Specification spec, Pageable page);


}
