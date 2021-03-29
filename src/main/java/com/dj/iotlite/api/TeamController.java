package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.AuthService;
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

    @Autowired
    AuthService authService;
    /**
     * 我的团队
     *
     * @param query
     * @return
     */
    @GetMapping("/list")
    public ResDto<Page<TeamListDto>> list(TeamQueryForm query) {
        Page<TeamListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Team> res = teamService.list(query);
        res.forEach(s -> {
            TeamListDto t = new TeamListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

    /**
     * 我创建的团队
     *
     * @param query
     * @return
     */
    @GetMapping("/listofOwner")
    public ResDto<Page<UserListDto>> listofOwner(TeamQueryForm query) {
        Page<TeamListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Team> res = teamService.listofOwner(query);
        res.forEach(s -> {
            TeamListDto t = new TeamListDto();
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
     *
     * @param form
     * @return
     */
    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody TeamForm form) {
        return success(teamService.save(form,authService.getUserInfo()));
    }

    @GetMapping("/info")
    public ResDto<TeamDto> query(@RequestParam("sn") String sn) {
        return success(teamService.queryTeam(sn,authService.getUserInfo()));
    }
}
