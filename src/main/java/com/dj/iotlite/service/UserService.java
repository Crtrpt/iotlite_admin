package com.dj.iotlite.service;

import com.dj.iotlite.entity.organization.OrganizationRepository;
import com.dj.iotlite.entity.organization.OrganizationUserRepository;
import com.dj.iotlite.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationUserRepository organizationUserRepository;
}
