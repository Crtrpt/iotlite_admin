package com.dj.iotlite.mapper;

import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeviceMapper {


    Page<Device> getGroupDeviceList(@Param("deviceQueryForm") DeviceQueryForm deviceQueryForm);

}
