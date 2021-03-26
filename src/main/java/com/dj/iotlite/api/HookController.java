package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.HookSaveForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.service.HookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hook")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class HookController extends BaseController {

    @Autowired
    HookService hookService;

    @PostMapping("/save")
    public ResDto<Boolean> version(@RequestBody HookSaveForm form) {
        return success(hookService.save(form));
    }


}
