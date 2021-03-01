package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.adaptor.Adaptor;
import com.dj.iotlite.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdaptorRepository extends
        CrudRepository<Adaptor, Long>,
        JpaSpecificationExecutor<Adaptor>,
        JpaRepository<Adaptor, Long> {

    Optional<Adaptor> findFirstByName(String name);
}
