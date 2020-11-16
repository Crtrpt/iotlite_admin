package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.OrganizationDto;
import com.dj.iotlite.api.dto.OrganizationListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrganizationController extends BaseController {

    @Autowired
    UserService deviceService;

    @GetMapping("/list")
    public ResDto<List<OrganizationListDto>> list() {
        return success(deviceService.getOrganizationList());
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(deviceService.removeOrganization(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(deviceService.saveOrganization());
    }

    @GetMapping("/query")
    public ResDto<OrganizationDto> query(@RequestParam("uuid") String uuid) {
        return success(deviceService.queryOrganization(uuid));
    }
}
