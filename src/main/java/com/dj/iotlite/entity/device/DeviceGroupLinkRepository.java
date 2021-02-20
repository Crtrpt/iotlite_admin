package com.dj.iotlite.entity.device;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupLinkRepository extends CrudRepository<DeviceGroupLink, Long>,
        JpaSpecificationExecutor<DeviceGroupLink>,
        JpaRepository<DeviceGroupLink, Long> {
    Page<DeviceGroupLink> findAll(Specification spec, Pageable page);

    Long countByGroupId(Long groupId);
}
