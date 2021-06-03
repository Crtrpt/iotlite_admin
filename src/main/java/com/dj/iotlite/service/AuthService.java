package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.api.dto.LoginDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.form.ConfirmEmailForm;
import com.dj.iotlite.api.form.LoginForm;
import com.dj.iotlite.api.form.ResetPasswordForm;
import com.dj.iotlite.api.form.SigninForm;
import com.dj.iotlite.entity.repo.UserRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.exception.BusinessExceptionEnum;
import com.dj.iotlite.utils.PasswordUtils;
import com.dj.iotlite.utils.UUID;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.Random;

import static com.dj.iotlite.RedisKey.emailCode;

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
            u1.setPassword(PasswordUtils.Hash(PasswordUtils.Hash("admin")));
            userRepository.save(u1);
        });

        return true;
    }

    public Object signup(SigninForm form) {
        userRepository.findFirstByPhone(form.getPhone()).ifPresent(u -> {
            throw new BusinessException("phone ready exits");
        });
        userRepository.findFirstByEmail(form.getPhone()).ifPresent(u -> {
            throw new BusinessException("email ready exits");
        });
        userRepository.findFirstByAccount(form.getAccount()).ifPresentOrElse(u -> {
            throw new BusinessException("account ready exits");
        }, () -> {
            var newUser = new User();
            newUser.setAccount(form.getAccount());
            newUser.setName(form.getUsername());
            newUser.setPassword(PasswordUtils.Hash(form.getPassword()));
            newUser.setEmail(form.getEmail());
            newUser.setPhone(form.getPhone());
            userRepository.save(newUser);
        });
        return true;
    }


    @Autowired
    HttpServletRequest httpServletRequest;


    public User getUserInfo() {
        String token = "";
        token = httpServletRequest.getHeader("Authorization").split(" ")[1];

        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_HAS_BEEN_LOGINED_IN_FROM_ELSEWHERE);
        }

        var key = String.format(RedisKey.TOKEN, token);

        var userId = redisCommands.get(key);

        if (ObjectUtils.isEmpty(userId)) {
            throw new BusinessException("token error");
        }

        return userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> {
            throw new BusinessException("user not found");
        });
    }

    @Autowired
    JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    String from;

    public Object confirmEmail(ConfirmEmailForm form) {
        log.info("找回邮箱"+form.getEmail());
        userRepository.findFirstByEmail(form.getEmail()).orElseThrow(()->{throw new BusinessException("email not found");});
        var r= new Random();
        var code=r.nextInt(10000);
        redisCommands.set(String.format(emailCode,form.getEmail()),String.valueOf(code), new SetArgs().ex(1000*60));
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            //邮件接收人
            simpleMailMessage.setTo(form.getEmail());
            //邮件主题
            simpleMailMessage.setSubject("找回密码-验证码");
            //邮件内容
            simpleMailMessage.setText("验证码:" + code + "  有效期：60秒");
            javaMailSender.send(simpleMailMessage);
            log.info("发送邮件完成");
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
        }
        return true;
    }

    public Object resetpassword(ResetPasswordForm form) {
        var user = userRepository.findFirstByEmail(form.getEmail()).orElseThrow(() -> {
            throw new BusinessException("email not found");
        });
        var code = (String) redisCommands.get(String.format(emailCode, form.getEmail()));
        if (!form.getCode().equals(code)) {
            throw new BusinessException("code error");
        } else {
            user.setPassword(PasswordUtils.Hash(form.getPassword()));
            userRepository.save(user);
        }
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(from);
            //邮件接收人
            simpleMailMessage.setTo(form.getEmail());
            //邮件主题
            simpleMailMessage.setSubject("找回密码-确认修改");
            //邮件内容
            simpleMailMessage.setText("您的密码已经修改请重新登录");
            javaMailSender.send(simpleMailMessage);
            log.info("发生邮件完成");
        } catch (Exception e) {
            log.error("邮件发送失败 {}", e.getMessage());
        }
        return true;
    }
}
