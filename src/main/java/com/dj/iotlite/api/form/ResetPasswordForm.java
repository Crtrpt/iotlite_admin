package com.dj.iotlite.api.form;

import lombok.Data;

@Data
public class ResetPasswordForm {
    String email;
    String password;
    String code;
}
