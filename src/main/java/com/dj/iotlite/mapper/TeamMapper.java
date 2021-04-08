package com.dj.iotlite.mapper;

import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface TeamMapper {

    @Select("select user.* from user left join team_member on user.id=team_member.user_id where  team_member.team_id=#{teamId} limit  #{offset} , #{limit}")
    List<User> getMyTeamMemberList(Long teamId, Integer offset, Integer limit);

    @Select("select count(id) from team_member where  team_member.team_id=#{teamId}")
    Long getMyTeamMemberListCount(Long teamId);


    @Select("select team.* from team left join team_member on team.id=team_member.id where  team_member.user_id=#{userId} limit  #{offset} , #{limit}")
    List<Team> getMyTeamList(Long userId, Integer offset, Integer limit);

    @Select("select count(*) from  team_member where team_member.user_id=#{userId}")
    Long getMyTeamListCount(Long userId);

}
