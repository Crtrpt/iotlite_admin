package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.DeviceActionForm;
import com.dj.iotlite.api.form.DeviceLogQueryForm;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.service.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DeviceController extends BaseController {

    @Autowired
    DeviceService deviceService;

    @GetMapping("/list")
    public ResDto<Page<DeviceListDto>> list(DeviceQueryForm deviceQueryForm) {
        Page<DeviceListDto> ret = new Page<DeviceListDto>();
        org.springframework.data.domain.Page<Device> res = deviceService.getDeviceList(deviceQueryForm);
        res.forEach(s -> {
            DeviceListDto t = new DeviceListDto();
            BeanUtils.copyProperties(s, t);
            deviceService.getProduct(s.getProductId(), t.getProduct());
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    @GetMapping("/log")
    public ResDto<Page<DeviceLogDto>> getLogList(DeviceLogQueryForm query) {
        Page<DeviceLogDto> ret = new Page<>();
        org.springframework.data.domain.Page<DeviceLog> res = deviceService.getDeviceLogList(query);
        res.forEach(s -> {
            DeviceLogDto t = new DeviceLogDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    @PostMapping("/remove")
    public ResDto<Boolean> remove(@RequestParam("uuid") String uuid) {
        return success(deviceService.removeDevice(uuid));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody DeviceDto deviceDto) {
        return success(deviceService.saveDevice(deviceDto));
    }

    @GetMapping("/info")
    public ResDto<DeviceDto> query(@RequestParam("id") Long id) {
        return success(deviceService.queryDevice(id));
    }

    @PostMapping("/action")
    public ResDto<AsynPage> action(@RequestBody DeviceActionForm action) {
        return success(deviceService.action(action));
    }

    @GetMapping("/enable")
    public ResDto<AsynPage> enable(@RequestParam("uuid") String uuid) {
        return success(deviceService.enable(uuid));
    }
}
