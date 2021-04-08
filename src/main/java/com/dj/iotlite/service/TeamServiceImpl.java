package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.TeamDto;
import com.dj.iotlite.api.dto.TeamListDto;
import com.dj.iotlite.api.dto.TeamMemberListDto;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamMemberQueryForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.entity.repo.TeamMemberRepository;
import com.dj.iotlite.entity.repo.TeamRepository;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.TeamMember;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.mapper.TeamMapper;
import com.dj.iotlite.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Boolean save(TeamForm form, User user) {
        var team = new Team();
        BeanUtils.copyProperties(form, team);
        team.setSn(UUID.getUUID());
        team.setOwner(user.getId());
        team.setCreatedAt(System.currentTimeMillis());
        team.setMemberCount(1L);
        teamRepository.save(team);

        var teamMember = new TeamMember();
        teamMember.setTeamId(team.getId());
        teamMember.setUserId(user.getId());
        teamMember.setCreatedAt(System.currentTimeMillis());
        teamMemberRepository.save(teamMember);
        return true;
    }

    @Override
    public Boolean remove(Long id) {
        return null;
    }

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired(required = false)
    TeamMapper teamMapper;

    @Override
    public Page<TeamListDto> list(TeamQueryForm query, User userInfo) {


        Page<TeamListDto> res = new Page<TeamListDto>();
        var users = teamMapper.getMyTeamList(userInfo.getId()
                , (query.getPageSize()) * (query.getPageNum() - 1)
                , query.getPageSize());
        users.forEach(s -> {
            var t1 = new TeamListDto();
            BeanUtils.copyProperties(s, t1);
            res.getList().add(t1);
        });

        res.setTotal(teamMapper.getMyTeamListCount(userInfo.getId()));
        return res;
    }


    @Override
    public Object queryTeam(String sn, User userInfo) {
        var team = teamRepository.findFirstBySn(sn).orElseThrow(() -> {
            throw new BusinessException("not found team");
        });
        teamMemberRepository.findFirstByUserIdAndTeamId(userInfo.getId(), team.getId()).orElseThrow(() -> {
            throw new BusinessException("not found team");
        });
        var res = new TeamDto();
        BeanUtils.copyProperties(team, res);
        return res;
    }


    @Override
    public com.dj.iotlite.api.dto.Page<TeamMemberListDto> getTeamMemberList(TeamMemberQueryForm query, User userInfo) {
        var t = teamRepository.findFirstBySn(query.getSn()).get();

        com.dj.iotlite.api.dto.Page<TeamMemberListDto> res = new com.dj.iotlite.api.dto.Page<TeamMemberListDto>();
        var users = teamMapper.getMyTeamMemberList(t.getId()
                , (query.getPageSize()) * (query.getPageNum() - 1)
                , query.getPageSize());
        users.forEach(s -> {
            var t1 = new TeamMemberListDto();
            BeanUtils.copyProperties(s, t1);
            res.getList().add(t1);
        });

        res.setTotal(teamMapper.getMyTeamMemberListCount(userInfo.getId()));
        return res;
    }


}
