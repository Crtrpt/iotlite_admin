package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.TeamForm;
import com.dj.iotlite.api.form.TeamMemberQueryForm;
import com.dj.iotlite.api.form.TeamQueryForm;
import com.dj.iotlite.entity.user.Team;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.service.AuthService;
import com.dj.iotlite.service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberController extends BaseController {

    @Autowired
    TeamService teamService;

    @Autowired
    AuthService authService;

    @GetMapping("/list")
    public ResDto<Page<TeamMemberListDto>> memberList(TeamMemberQueryForm query) {
        Page<TeamMemberListDto> ret  = teamService.getTeamMemberList(query,authService.getUserInfo());
        return success(ret);
    }
}
