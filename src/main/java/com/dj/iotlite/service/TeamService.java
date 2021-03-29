package com.dj.iotlite.service;

import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeamService {
    List<Team> queryByUserId(Long id);

    Boolean save(TeamForm form, User user);

    Boolean remove(Long id);

    Page<Team> list(TeamQueryForm query);

    Page<Team> listofOwner(TeamQueryForm query);

    Object queryTeam(String sn, User userInfo);
}
