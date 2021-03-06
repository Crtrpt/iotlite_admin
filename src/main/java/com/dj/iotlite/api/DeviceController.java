package com.dj.iotlite.api;

import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceGroup;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.enums.RegTypeEnum;
import com.dj.iotlite.service.AuthService;
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

    @Autowired
    AuthService authService;

    @GetMapping("/list")
    public ResDto<Page<DeviceListDto>> list(DeviceQueryForm deviceQueryForm) {
        Page<DeviceListDto> ret = new Page<>();
        org.springframework.data.domain.Page<Device> res = deviceService.getDeviceList(deviceQueryForm, authService.getUserInfo());
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
        org.springframework.data.domain.Page<DeviceGroup> res = deviceService.getDeviceGroupList(deviceQueryForm,authService.getUserInfo());
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
        Page<DeviceListDto> ret = deviceService.getGroupDeviceList(deviceQueryForm,authService.getUserInfo());
        return success(ret);
    }

    @GetMapping("/groupInfo")
    public ResDto<DeviceGroupDto> queryGroupInfo(@RequestParam("id") Long id) {
        return success(deviceService.queryDeviceGroup(id,authService.getUserInfo()));
    }

    @PostMapping("/groupRemove")
    public ResDto<DeviceGroupDto> groupRemove(@RequestBody DeviceGroupRemoveForm groupRemoveForm) {
        return success(deviceService.groupRemove(groupRemoveForm,authService.getUserInfo()));
    }

    @PostMapping("/groupStateClean")
    public ResDto<Boolean> groupStateClean(@RequestBody DeviceGroupCleanForm deviceGroupCleanForm) {
        return success(deviceService.groupStateClean(deviceGroupCleanForm,authService.getUserInfo()));
    }

    @GetMapping("/groupInfoByName")
    public ResDto<DeviceGroupDto> queryGroupInfo(@RequestParam("name") String name) {
        return success(deviceService.queryDeviceGroup(name,authService.getUserInfo()));
    }

    @PostMapping("/saveGroup")
    public ResDto<Boolean> save(@RequestBody DeviceGroupSaveForm deviceDto) {
        return success(deviceService.deviceGroupSave(deviceDto,authService.getUserInfo()));
    }

    @PostMapping("/saveGroupFence")
    public ResDto<Boolean> saveGroupFence(@RequestBody DeviceGroupFenceSaveForm form) {
        return success(deviceService.saveGroupFence(form,authService.getUserInfo()));
    }

    @PostMapping("/saveGroupPlayground")
    public ResDto<Boolean> saveGroupPlayground(@RequestBody DeviceGroupSpecSaveForm form) {
        return success(deviceService.saveGroupPlayground(form,authService.getUserInfo()));
    }

    @GetMapping("/log")
    public ResDto<Page<DeviceLogDto>> getLogList(DeviceLogQueryForm query) {
        Page<DeviceLogDto> ret = new Page<>();
        org.springframework.data.domain.Page<DeviceLog> res = deviceService.getDeviceLogList(query,authService.getUserInfo());
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
        return success(deviceService.removeDevice(form,authService.getUserInfo()));
    }

    @PostMapping("/save")
    public ResDto<Boolean> save(@RequestBody DeviceSaveForm deviceDto) {
        deviceDto.setRegType(RegTypeEnum.admin);
        return success(deviceService.saveDevice(deviceDto,authService.getUserInfo()));
    }

    @GetMapping("/info")
    public ResDto<DeviceDto> query(@RequestParam("id") Long id) {
        return success(deviceService.queryDevice(id,authService.getUserInfo()));
    }

    @PostMapping("/action")
    public ResDto<AsynPage> action(@RequestBody DeviceActionForm action) throws Exception {
        return success(deviceService.action(action,authService.getUserInfo()));
    }

    @GetMapping("/location")
    public ResDto<DeviceLocationDto> location(DeviceLocationForm action) {
        return success(deviceService.location(action,authService.getUserInfo()));
    }


    /**
     * 更新设备key
     *
     * @param form
     * @return
     */
    @PostMapping("/setDeviceMeta")
    public ResDto<Boolean> setDeviceMeta(@RequestBody DeviceMetaForm form) {
        return success(deviceService.setDeviceMeta(form,authService.getUserInfo()));
    }
    /**
     * 更新设备key
     *
     * @param form
     * @return
     */
    @PostMapping("/refreshDeviceKey")
    public ResDto<Boolean> refreshDeviceKey(@RequestBody DeviceRefreshDeviceKeyForm form) {
        return success(deviceService.refreshDeviceKey(form,authService.getUserInfo()));
    }

    @PostMapping("/changeTags")
    public ResDto<Boolean> changeTags(@RequestBody DeviceChangeTagsForm form) {
        return success(deviceService.deviceChangeTags(form,authService.getUserInfo()));
    }

    @PostMapping("/saveAccess")
    public ResDto<Boolean> saveAccess(@RequestBody DeviceSaveAccessForm form) {
        return success(deviceService.saveDeviceAccess(form,authService.getUserInfo()));
    }

    @PostMapping("/saveBase")
    public ResDto<Boolean> saveBase(@RequestBody DeviceSaveBaseForm form) {
        return success(deviceService.saveBase(form,authService.getUserInfo()));
    }

    @PostMapping("/saveGroupBase")
    public ResDto<Boolean> saveGroupBase(@RequestBody DeviceSaveGroupBaseForm form) {
        return success(deviceService.saveGroupBase(form,authService.getUserInfo()));
    }


    @PostMapping("/saveModel")
    public ResDto<Boolean> saveModel(@RequestBody DeviceSaveModelForm form) {
        return success(deviceService.saveDeviceModel(form,authService.getUserInfo()));
    }
}
