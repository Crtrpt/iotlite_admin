package com.dj.iotlite.mqtt;

import com.dj.iotlite.mqtt.listener.PublisherListener;
import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.InterceptHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class MqttServer {

    static Server mqttBroker = new Server();

    public static void main(String[] argv){ List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
        IResourceLoader classpathLoader = new ClasspathResourceLoader();
        final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
        mqttBroker.startServer(classPathConfig, userHandlers,new SslContextCreator(), new Authenticator(), new AuthorizatorPolicy());
        System.out.println("start mqtt server");
    }
}