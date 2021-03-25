package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.OptionDto;
import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ProductVersionListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.GetAllVersionForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/version")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class VersionController extends BaseController {

    @Autowired
    VersionService versionService;

    @PostMapping("/save")
    public ResDto<Boolean> version(@RequestBody  NewVersionReleaseForm form) {
        return success(versionService.newVersion(form));
    }

    @GetMapping("/all")
    public ResDto<List<OptionDto>> version(GetAllVersionForm query) {


        return success(versionService.getAll(query));
    }

    @GetMapping("/list")
    public ResDto<Page<ProductVersionListDto>> version(DeviceQueryForm query) {

        Page<ProductVersionListDto> ret = new Page<ProductVersionListDto>();
        org.springframework.data.domain.Page<ProductVersion> res = versionService.getVersionList(query);
        res.forEach(s -> {
            ProductVersionListDto t = new ProductVersionListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }
}
