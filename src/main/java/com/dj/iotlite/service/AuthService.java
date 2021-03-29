package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.api.dto.LoginDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.form.LoginForm;
import com.dj.iotlite.api.form.SigninForm;
import com.dj.iotlite.entity.repo.UserRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.exception.BusinessExceptionEnum;
import com.dj.iotlite.utils.PasswordUtils;
import com.dj.iotlite.utils.UUID;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public LoginDto login(LoginForm form) {
        User user = userRepository.findFirstByAccount(form.getAccount()).orElseThrow(() -> {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        });

        if (ObjectUtils.isEmpty(user.getPassword())) {
            throw new BusinessException("please reset your password");
        }

        if (!user.getPassword().equals(PasswordUtils.Hash(form.getPassword()))) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        LoginDto loginDto = new LoginDto();
        loginDto.setUser(userDto);
        loginDto.setToken(genToken(user));
        return loginDto;
    }

    @Autowired
    RedisCommands<String, String> redisCommands;

    private String genToken(User user) {
        var token = UUID.getUUID();
        var key = String.format(RedisKey.TOKEN, token);
        redisCommands.set(key, String.valueOf(user.getId()));
        return token;
    }


    public Object initAdmin() {

        userRepository.findFirstByAccount("admin").ifPresentOrElse((u) -> {
            throw new BusinessException("admin ready exits");
        }, () -> {
            var u1 = new User();
            u1.setAccount("admin");
            u1.setName("admin");
            u1.setPassword(PasswordUtils.Hash("admin"));
            userRepository.save(u1);
        });

        return true;
    }

    public Object signup(SigninForm form) {
        userRepository.findFirstByAccount(form.getAccount()).ifPresentOrElse(u -> {
            throw new BusinessException("account ready exits");
        }, () -> {
            var newUser = new User();
            newUser.setAccount(form.getAccount());
            newUser.setName(form.getUsername());
            newUser.setPassword(PasswordUtils.Hash(form.getPassword()));
            userRepository.save(newUser);
        });
        return true;
    }


    @Autowired
    HttpServletRequest httpServletRequest;


    public User getUserInfo() {
        String token = "";
        if (httpServletRequest.getMethod().equals("GET")) {
            token = httpServletRequest.getParameter("token");
        } else {
            token = httpServletRequest.getHeader("Authorization").split(" ")[1];
        }

        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_HAS_BEEN_LOGINED_IN_FROM_ELSEWHERE);
        }

        var key = String.format(RedisKey.TOKEN, token);

        var userId=redisCommands.get(key);

        if(ObjectUtils.isEmpty(userId)){
            throw new BusinessException("token error");
        }

        return userRepository.findById(Long.valueOf(userId)).orElseThrow(()->{
            throw new BusinessException("user not found");
        });
    }
}
