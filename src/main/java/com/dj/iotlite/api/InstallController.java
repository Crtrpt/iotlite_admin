package com.dj.iotlite.api;

import com.dj.iotlite.adaptor.IotliteMqttAdaptor.IotliteMqttImpl;
import com.dj.iotlite.api.dto.ResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/install")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class InstallController   extends BaseController{

    @Autowired
    IotliteMqttImpl iotliteMqtt;

    @GetMapping("/install")
    public ResDto<Object> install() throws Exception {
        iotliteMqtt.install();
        return success();
    }


}
