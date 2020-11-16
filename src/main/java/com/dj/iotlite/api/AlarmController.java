package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.AlarmListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlarmController extends BaseController {

    @Autowired
    TaskService taskService;

    @GetMapping("/list")
    public ResDto<AlarmListDto> list() {
        return success(taskService.getAlarmList());
    }

}
