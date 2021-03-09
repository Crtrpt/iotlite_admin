package com.dj.iotlite.adaptor;

import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.adaptor.IotliteMqttAdaptor.PushCallback;
import com.dj.iotlite.push.Authenticator;
import com.dj.iotlite.push.AuthorizatorPolicy;
import com.dj.iotlite.push.PublisherListener;
import com.dj.iotlite.push.SslContextCreator;
import com.dj.iotlite.service.AdaptorService;
import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.InterceptHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Data
@Slf4j
public class IotliteMqttImpl implements Adaptor {

    String name = "IOTLITE_MQTT";

    @Autowired
    AdaptorService adaptorService;

    @Value("${app.mqtt.serverURI}")
    String serverURI;

    @Value("${app.mqtt.clientId}")
    String clientId;

    @PostConstruct
    void init() throws MqttException {
        System.out.println("启动mqtt适配器");
        initMqttBroker();
        initMqttClient();
    }


    MqttClient mqttClient;

    private final Server mqttBroker = new Server();

    void initMqttBroker() {
        log.info("启动mqtt适配broker");
        List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
        IResourceLoader classpathLoader = new ClasspathResourceLoader("./mqtt_adaptor.conf");
        final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
        mqttBroker.startServer(classPathConfig, userHandlers, new SslContextCreator(), new Authenticator(), new AuthorizatorPolicy());
    }

    void initMqttClient() throws MqttException {
        log.info("启动mqtt客户端");
        var option = new MqttConnectOptions();
        option.setCleanSession(false);
        option.setAutomaticReconnect(true);

        mqttClient = new MqttClient(serverURI, clientId, new MemoryPersistence());
        mqttClient.setCallback(new PushCallback(mqttClient));
        mqttClient.connect(option);

    }

    @Override
    public void install() {
        adaptorService.install(getName(), this.getClass().getSimpleName(), new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }

    @Override
    public void publish(Product product, Device device, String topic, String data) throws Exception {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(data.getBytes(StandardCharsets.UTF_8));
        mqttClient.publish(topic, mqttMessage);
    }
}
