package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.AlarmListDto;
import com.dj.iotlite.api.dto.Page;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.api.form.AlarmQueryForm;
import com.dj.iotlite.entity.alarm.Alarm;
import com.dj.iotlite.service.TaskService;
import org.springframework.beans.BeanUtils;
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
    public ResDto<Page<AlarmListDto>> list(AlarmQueryForm query) {
        Page<AlarmListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Alarm> res = taskService.getAlarmList(query);
        res.forEach(s -> {
            AlarmListDto t = new AlarmListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        return success(ret);
    }

}
