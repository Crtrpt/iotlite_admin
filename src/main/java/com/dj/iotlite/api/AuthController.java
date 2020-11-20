package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.dto.LoginDto;
import com.dj.iotlite.api.form.LoginForm;
import com.dj.iotlite.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResDto<LoginDto> login(@RequestBody LoginForm Loginform) {

        return success(authService.login(Loginform));
    }


}
