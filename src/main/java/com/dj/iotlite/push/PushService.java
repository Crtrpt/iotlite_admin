package com.dj.iotlite.push;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.moquette.interception.InterceptHandler;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
public class PushService {

    private final Server mqttBroker = new Server();

    @PostConstruct
    public void init(){
        log.info("启动自带mqtt 服务器");
        List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
        IResourceLoader classpathLoader = new ClasspathResourceLoader();
        final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
        mqttBroker.startServer(classPathConfig, userHandlers,new SslContextCreator(), new Authenticator(), new AuthorizatorPolicy());
    }

    public void  push(String topic, String data){
        MqttPublishMessage message = MqttMessageBuilders.publish()
                .topicName(topic)
                .retained(false)
                .qos(MqttQoS.AT_LEAST_ONCE)
                .payload(Unpooled.copiedBuffer(data.getBytes(UTF_8)))
                .build();
        log.info("推送:" + topic + " 数据: " + data);
        mqttBroker.internalPublish(message, "INTRLPUB");
    }
}
