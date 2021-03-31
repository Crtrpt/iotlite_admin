package com.dj.iotlite.adaptor.UDPDemoAdaptor;

import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.service.AdaptorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@Getter
public class UDPDemoAdaptorImpl implements Adaptor {

    String name = "IOTLITE_UDP_DEMO";

    @Autowired
    AdaptorService adaptorService;

    @PostConstruct
    void init() {
        log.info("启动udp演示服务器");
        initHttpServer();
    }

    private void initHttpServer() {
    }

    @Override
    public void install() {
        adaptorService.install(getName(), this.getClass().getSimpleName(), new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }
}
