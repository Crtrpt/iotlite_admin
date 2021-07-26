package com.dj.iotlite.mapper;

import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

@Mapper
public interface DeviceMapper {

    @Results(id="Device",value = {
            @Result(column = "access",typeHandler = EnumOrdinalTypeHandler.class),
    })
    Page<Device> getGroupDeviceList(@Param("deviceQueryForm") DeviceQueryForm deviceQueryForm);

}
