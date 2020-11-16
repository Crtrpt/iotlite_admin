package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.ProductListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.dto.UserListDto;
import com.dj.iotlite.service.TaskService;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public ResDto<List<UserListDto>> list() {
        return success(userService.getUserList());
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(userService.removeUser(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(userService.saveUser());
    }

    @GetMapping("/query")
    public ResDto<UserDto> query(@RequestParam("uuid") String uuid) {
        return success(userService.queryUser(uuid));
    }
}
