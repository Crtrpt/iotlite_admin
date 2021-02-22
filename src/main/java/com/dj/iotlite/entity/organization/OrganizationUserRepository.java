package com.dj.iotlite.entity.organization;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationUserRepository extends
        CrudRepository<OrganizationUser, Long>,
        JpaSpecificationExecutor<OrganizationUser>,
        JpaRepository<OrganizationUser, Long> {

}
