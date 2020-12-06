package com.dj.iotlite.utils;

import org.springframework.util.DigestUtils;

public class PasswordUtils {
    static public String md5(String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        return md5;
    }
}
