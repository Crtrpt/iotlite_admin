package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.TeamListDto;
import com.dj.iotlite.api.dto.TeamMemberListDto;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamMemberQueryForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;



import java.util.List;

public interface TeamService {
    List<Team> queryByUserId(Long id);

    Boolean save(TeamForm form, User user);

    Boolean remove(Long id);

    Page<TeamListDto> list(TeamQueryForm query, User userInfo);

    Object queryTeam(String sn, User userInfo);

    Page<TeamMemberListDto> getTeamMemberList(TeamMemberQueryForm query, User userInfo);
}
