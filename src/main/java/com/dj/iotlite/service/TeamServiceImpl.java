package com.dj.iotlite.service;

import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
    @Override
    public List<Team> queryByUserId(Long id) {
        return null;
    }

    @Override
    public Boolean save(TeamForm form) {
        return null;
    }

    @Override
    public Boolean remove(Long id) {
        return null;
    }

    @Override
    public Page<Team> list(UserQueryForm query) {
        return null;
    }
}
