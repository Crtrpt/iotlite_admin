package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.dto.UserListDto;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResDto<Page<UserListDto>> list(UserQueryForm query) {
        Page<UserListDto> ret = new Page<>();
        org.springframework.data.domain.Page<User> res = userService.getUserList(query);
        res.forEach(s -> {
            UserListDto t = new UserListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(userService.removeUser(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody UserForm userForm) {
        return success(userService.saveUser(userForm));
    }

    @GetMapping("/query")
    public ResDto<UserDto> query(@RequestParam("uuid") String uuid) {
        return success(userService.queryUser(uuid));
    }
}
