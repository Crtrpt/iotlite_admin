package com.dj.iotlite.entity.setting;


import com.dj.iotlite.entity.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends
        CrudRepository<Setting, Long>,
        JpaSpecificationExecutor<Setting>,
        JpaRepository<Setting, Long> {

}
