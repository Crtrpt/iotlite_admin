package com.dj.iotlite.service;

import com.dj.iotlite.api.form.LoginForm;
import com.dj.iotlite.entity.task.TaskRepository;
import com.dj.iotlite.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public Object login(LoginForm loginform) {
        return null;
    }
}
