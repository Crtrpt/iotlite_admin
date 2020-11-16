package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.AlarmListDto;
import com.dj.iotlite.api.dto.DeviceDto;
import com.dj.iotlite.api.dto.DeviceListDto;
import com.dj.iotlite.api.dto.ResDto;
import com.dj.iotlite.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DeviceController extends BaseController {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/list")
    public ResDto<List<DeviceListDto>> list() {
        return success(deviceService.getDeviceList());
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(deviceService.removeDevice(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save() {
        return success(deviceService.saveDevice());
    }

    @GetMapping("/query")
    public ResDto<DeviceDto> query(@RequestParam("uuid") String uuid) {
        return success(deviceService.queryDevice(uuid));
    }
}
