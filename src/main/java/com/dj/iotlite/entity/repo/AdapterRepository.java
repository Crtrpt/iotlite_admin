package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.adaptor.Adapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdapterRepository extends
        CrudRepository<Adapter, Long>,
        JpaSpecificationExecutor<Adapter>,
        JpaRepository<Adapter, Long> {

    Optional<Adapter> findFirstByName(String name);
}
