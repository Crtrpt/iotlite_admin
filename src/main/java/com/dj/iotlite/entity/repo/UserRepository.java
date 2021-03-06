package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.user.User;
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

    Optional<User> findFirstByAccount(String account);

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByPhone(String phone);

    Integer countAllByOrganizationId(Long organizationId);
}
