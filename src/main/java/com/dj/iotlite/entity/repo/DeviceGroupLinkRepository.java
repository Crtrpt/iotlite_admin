package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.device.DeviceGroupLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceGroupLinkRepository extends CrudRepository<DeviceGroupLink, Long>,
        JpaSpecificationExecutor<DeviceGroupLink>,
        JpaRepository<DeviceGroupLink, Long> {
    Page<DeviceGroupLink> findAll(Specification spec, Pageable page);

    Long countByGroupId(Long groupId);

    List<DeviceGroupLink> findAllByProductSnAndDeviceSn(String productSn, String deviceSn);

    List<DeviceGroupLink> findAllByProductSn(String productSn);

    List<DeviceGroupLink> findAllByGroupId(Long groupId);
}
