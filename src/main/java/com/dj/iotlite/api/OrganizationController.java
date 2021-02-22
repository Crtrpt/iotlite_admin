package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.OrganizationDto;
import com.dj.iotlite.api.dto.OrganizationListDto;
import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.OrganizationForm;
import com.dj.iotlite.api.form.OrganizationQueryForm;
import com.dj.iotlite.entity.organization.Organization;
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
    UserService userService;

    @GetMapping("/list")
    public ResDto<Page<OrganizationListDto>> list(OrganizationQueryForm query) {
        Page<OrganizationListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Organization> res = userService.getOrganizationList(query);
        res.forEach(s -> {
            OrganizationListDto t = new OrganizationListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        System.out.println(res);
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(userService.removeOrganization(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody OrganizationForm organizationForm) {
        return success(userService.saveOrganization(organizationForm));
    }

    @GetMapping("/query")
    public ResDto<OrganizationDto> query(@RequestParam("uuid") String uuid) {
        return success(userService.queryOrganization(uuid));
    }

    @GetMapping("/tree")
    public ResDto<List<OrganizationDto>> Tree(@RequestParam(value = "id" ,defaultValue = "") Long id) {
        return success(userService.queryOrganizationTree(id));
    }
}
