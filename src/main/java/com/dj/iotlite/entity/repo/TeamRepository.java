package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends
        CrudRepository<Team, Long>,
        JpaSpecificationExecutor<Team>,
        JpaRepository<Team, Long> {
    Optional<Team> findFirstBySn(String sn);
}
