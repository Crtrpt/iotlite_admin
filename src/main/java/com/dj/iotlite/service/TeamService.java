package com.dj.iotlite.service;

import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.Team;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeamService {
    List<Team> queryByUserId(Long id);

    Boolean save(TeamForm form);

    Boolean remove(Long id);

    Page<Team> list(UserQueryForm query);
}
