package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.dto.UserListDto;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.TeamService;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TeamController extends BaseController {

    @Autowired
    TeamService teamService;

    @GetMapping("/list")
    public ResDto<Page<UserListDto>> list(UserQueryForm query) {
        Page<UserListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Team> res = teamService.list(query);
        res.forEach(s -> {
            UserListDto t = new UserListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("id") Long id) {
        return success(teamService.remove(id));
    }
    /**
     * 创建团队
     * @param form
     * @return
     */
    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody TeamForm form) {
        return success(teamService.save(form));
    }
}
