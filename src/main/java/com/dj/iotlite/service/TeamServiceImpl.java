package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.TeamDto;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.entity.repo.TeamMemberRepository;
import com.dj.iotlite.entity.repo.TeamRepository;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.TeamMember;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    @Override
    public Page<Team> list(TeamQueryForm form) {
        Specification<Team> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(form.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + form.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + form.getWords() + "%")
                ));
            }

            //我是团队的拥有者

            //我是团队的成员
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };

        return teamRepository.findAll(specification, form.getPage());
    }

    @Override
    public Page<Team> listofOwner(TeamQueryForm form) {
        Specification<Team> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(form.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + form.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + form.getWords() + "%")
                ));
            }

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };

        return teamRepository.findAll(specification, form.getPage());
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
}
