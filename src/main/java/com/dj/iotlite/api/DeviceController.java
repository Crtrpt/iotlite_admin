package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceGroup;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.enums.RegTypeEnum;
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
        Page<DeviceListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Device> res = deviceService.getDeviceList(deviceQueryForm);
        res.forEach(s -> {
            DeviceListDto t = new DeviceListDto();
            BeanUtils.copyProperties(s, t);
            deviceService.getProduct(s.getProductSn(), t.getProduct());
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

    @PostMapping("/groupRemove")
    public ResDto<DeviceGroupDto> groupRemove(@RequestBody DeviceGroupRemoveForm groupRemoveForm) {
        return success(deviceService.groupRemove(groupRemoveForm));
    }

    @PostMapping("/groupStateClean")
    public ResDto<Boolean> groupStateClean(@RequestBody DeviceGroupCleanForm deviceGroupCleanForm) {
        return success(deviceService.groupStateClean(deviceGroupCleanForm));
    }

    @GetMapping("/groupInfoByName")
    public ResDto<DeviceGroupDto> queryGroupInfo(@RequestParam("name") String name) {
        return success(deviceService.queryDeviceGroup(name));
    }

    @PostMapping("/saveGroup")
    public ResDto<Boolean> save(@RequestBody DeviceGroupSaveForm deviceDto) {
        return success(deviceService.deviceGroupSave(deviceDto));
    }

    @PostMapping("/saveGroupFence")
    public ResDto<Boolean> saveGroupFence(@RequestBody DeviceGroupFenceSaveForm form) {
        return success(deviceService.saveGroupFence(form));
    }

    @PostMapping("/saveGroupPlayground")
    public ResDto<Boolean> saveGroupPlayground(@RequestBody DeviceGroupSpecSaveForm form) {
        return success(deviceService.saveGroupPlayground(form));
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
    public ResDto<Boolean> remove(@RequestBody DeviceRemoveForm form) {
        return success(deviceService.removeDevice(form));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody DeviceSaveForm deviceDto) {
        deviceDto.setRegType(RegTypeEnum.admin);
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


    /**
     * 更新设备key
     *
     * @param form
     * @return
     */
    @PostMapping("/setDeviceMeta")
    public ResDto<Boolean> setDeviceMeta(@RequestBody DeviceMetaForm form) {
        return success(deviceService.setDeviceMeta(form));
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

    @PostMapping("/saveAccess")
    public ResDto<Boolean> saveAccess(@RequestBody DeviceSaveAccessForm form) {
        return success(deviceService.saveDeviceAccess(form));
    }

    @PostMapping("/saveBase")
    public ResDto<Boolean> saveBase(@RequestBody DeviceSaveBaseForm form) {
        return success(deviceService.saveBase(form));
    }

    @PostMapping("/saveGroupBase")
    public ResDto<Boolean> saveGroupBase(@RequestBody DeviceSaveGroupBaseForm form) {
        return success(deviceService.saveGroupBase(form));
    }


    @PostMapping("/saveModel")
    public ResDto<Boolean> saveModel(@RequestBody DeviceSaveModelForm form) {
        return success(deviceService.saveDeviceModel(form));
    }
}
