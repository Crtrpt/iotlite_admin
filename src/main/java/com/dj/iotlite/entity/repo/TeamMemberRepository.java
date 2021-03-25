package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.user.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends
        CrudRepository<TeamMember, Long>,
        JpaSpecificationExecutor<TeamMember>,
        JpaRepository<TeamMember, Long> {
}
