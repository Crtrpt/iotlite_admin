package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.OrganizationQueryForm;
import com.dj.iotlite.entity.organization.Organization;
import com.dj.iotlite.entity.product.Gateway;
import com.dj.iotlite.service.UserService;
import org.springframework.beans.BeanUtils;
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
    public ResDto<Page<OrganizationListDto>> list(OrganizationQueryForm query) {
        Page<OrganizationListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Organization> res = deviceService.getOrganizationList(query);
        res.forEach(s -> {
            OrganizationListDto t = new OrganizationListDto();
            BeanUtils.copyProperties(s, t);
            ret.getData().add(t);
        });
        return success(ret);
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
