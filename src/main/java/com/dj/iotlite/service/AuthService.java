package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.LoginDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.form.LoginForm;
import com.dj.iotlite.entity.repo.UserRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.exception.BusinessExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public LoginDto login(LoginForm loginform) {
        User user = userRepository.findFirstByAccount(loginform.getAccount()).orElseThrow(() -> {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        });

        if (user.getPassword().equals(PasswordHash(loginform.getPassword()))) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_NOT_FOUND);
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        LoginDto loginDto = new LoginDto();
        loginDto.setUser(userDto);
        loginDto.setToken(genToken(user));
        return loginDto;
    }

    private String genToken(User user) {
        return String.valueOf(System.currentTimeMillis());
    }

    //获取hash后的二维码
    private String PasswordHash(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
