package com.dj.iotlite.service;

import com.dj.iotlite.entity.user.User;

import java.util.List;

public interface MemberService {
    List<User> getTeamMember(Long teamId);
}
