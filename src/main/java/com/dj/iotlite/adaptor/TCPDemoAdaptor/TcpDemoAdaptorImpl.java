package com.dj.iotlite.adaptor.TCPDemoAdaptor;

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
public class TcpDemoAdaptorImpl implements Adaptor {

    String name = "IOTLITE_TCP_DEMO";

    @Autowired
    AdaptorService adaptorService;

    @PostConstruct
    void init() {
        log.info("启动tcp演示服务器");
    }


    @Override
    public void install() {
        adaptorService.install(getName(),this.getClass().getSimpleName(),new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }

    @Override
    public void publish(Optional<Device> proxy, ProductVersion product, Device device, String topic, String data) throws Exception {
        log.info("发生给客户端");
    }
}
