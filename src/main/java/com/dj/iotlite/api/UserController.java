package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.AuthService;
import com.dj.iotlite.service.TeamService;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    TeamService teamService;

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

    @GetMapping("/teams")
    public ResDto<List<TeamListDto>> query(@RequestParam("id") Long id) {
        return success(teamService.queryByUserId(id));
    }

    @GetMapping("/profile")
    public ResDto<UserDto> getProfile() {
        return success(userService.getProfile(authService.getUserInfo()));
    }

    @PostMapping("/profile")
    public ResDto<UserDto> saveProfile(@RequestBody UserDto userDto) {
        return success(userService.saveProfile(authService.getUserInfo(),userDto));
    }

    /**
     * upload image
     * @param dataFile
     * @return
     */
    @RequestMapping("/image")
    public ResDto upload(@RequestPart("file") @NotNull @NotBlank MultipartFile dataFile,
                         @RequestPart("id") Long userId){
        return success(userService.updateAvatarImage(dataFile,userId));
    }



}
