package com.dj.iotlite.adaptor.IotlitHttpAdaptor;

import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.service.AdaptorService;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
public class IotlitHttpImpl implements Adaptor {
    
    @Autowired
    AdaptorService adaptorService;

    @PostConstruct
    void init() {
        System.out.println("启动http服务器");
        initHttpServer();
    }

    private void initHttpServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(4302, "0.0.0.0")
                .setHandler(new MessageCallback()).build();
        server.start();
    }

    @Override
    public void install() {
        adaptorService.install("IOTLITE_HTTP",this.getClass().getSimpleName(),new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall("IOTLITE_HTTP");
    }

    @Override
    public void publish(Optional<Device> proxy, ProductVersion product, Device device, String topic, String data) throws Exception {
        log.info("发生给客户端");
    }
}
