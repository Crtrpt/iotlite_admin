package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceGroup;
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

    @GetMapping("/groupList")
    public ResDto<Page<DeviceGroupListDto>> grouplist(DeviceQueryForm deviceQueryForm) {
        Page<DeviceGroupListDto> ret = new Page<DeviceGroupListDto>();
        org.springframework.data.domain.Page<DeviceGroup> res = deviceService.getDeviceGroupList(deviceQueryForm);
        res.forEach(s -> {
            DeviceGroupListDto t = new DeviceGroupListDto();
            BeanUtils.copyProperties(s, t);
            ret.getList().add(t);
        });
        ret.setTotal(res.getTotalElements());
        return success(ret);
    }

    /**
     * 组内设备列表
     *
     * @param deviceQueryForm
     * @return
     */
    @GetMapping("/groupDeviceList")
    public ResDto<Page<DeviceListDto>> groupDevicelist(DeviceQueryForm deviceQueryForm) {
        Page<DeviceListDto> ret = deviceService.getGroupDeviceList(deviceQueryForm);
        return success(ret);
    }

    @GetMapping("/groupInfo")
    public ResDto<DeviceGroupDto> queryGroupInfo(@RequestParam("id") Long id) {
        return success(deviceService.queryDeviceGroup(id));
    }

    @GetMapping("/groupInfoByName")
    public ResDto<DeviceGroupDto> queryGroupInfo(@RequestParam("name") String name) {
        return success(deviceService.queryDeviceGroup(name));
    }

    @PostMapping("/saveGroup")
    public ResDto<Boolean> save(@RequestBody DeviceGroupSaveForm deviceDto) {
        return success(deviceService.deviceGroupSave(deviceDto));
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
    public ResDto<Boolean> save(@RequestBody DeviceSaveForm deviceDto) {
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

    @GetMapping("/location")
    public ResDto<DeviceLocationDto> location(DeviceLocationForm action) {
        return success(deviceService.location(action));
    }

    @GetMapping("/enable")
    public ResDto<AsynPage> enable(@RequestParam("uuid") String uuid) {
        return success(deviceService.enable(uuid));
    }

    /**
     * 更新设备key
     *
     * @param form
     * @return
     */
    @PostMapping("/refreshDeviceKey")
    public ResDto<Boolean> refreshDeviceKey(@RequestBody DeviceRefreshDeviceKeyForm form) {
        return success(deviceService.refreshDeviceKey(form));
    }

    @PostMapping("/changeTags")
    public ResDto<Boolean> changeTags(@RequestBody DeviceChangeTagsForm form) {
        return success(deviceService.deviceChangeTags(form));
    }
}
