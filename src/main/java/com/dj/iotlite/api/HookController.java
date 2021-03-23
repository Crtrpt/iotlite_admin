package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ProductVersionListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hook")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class HookController extends BaseController {


    @PostMapping("/save")
    public ResDto<Boolean> version(@RequestBody NewVersionReleaseForm form) {
        return success(true);
    }


}
