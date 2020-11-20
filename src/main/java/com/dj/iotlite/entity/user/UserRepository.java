package com.dj.iotlite.entity.user;


import com.dj.iotlite.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        CrudRepository<User, Long>,
        JpaSpecificationExecutor<User>,
        JpaRepository<User, Long> {

    Optional<User> findFirstByUuid(String uuid);
}
