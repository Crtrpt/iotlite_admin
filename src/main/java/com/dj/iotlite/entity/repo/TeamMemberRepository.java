package com.dj.iotlite.entity.repo;


import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends
        CrudRepository<TeamMember, Long>,
        JpaSpecificationExecutor<TeamMember>,
        JpaRepository<TeamMember, Long> {

    /**
     * 指定用户id 所在的全部 团队
     * @param userId
     * @return
     */
    List<TeamMember> findAllByUserId(Long userId);


    List<TeamMember> findAllByTeamId(Long teamId);
}
