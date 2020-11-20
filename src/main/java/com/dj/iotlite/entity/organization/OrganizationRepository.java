package com.dj.iotlite.entity.organization;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends
        CrudRepository<Organization, Long>,
        JpaSpecificationExecutor<Organization>,
        JpaRepository<Organization, Long> {

    Optional<Organization> findFirstByUuid(String uuid);

}
