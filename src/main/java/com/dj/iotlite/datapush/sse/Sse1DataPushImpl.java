package com.dj.iotlite.datapush.sse;

import com.dj.iotlite.datapush.DataPush;
import io.undertow.Undertow;
import io.undertow.server.handlers.sse.ServerSentEventHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xnio.channels.StreamSinkChannel;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@Data
public class Sse1DataPushImpl implements DataPush {

    /**
     * 所有订阅者
     */
    static public Map<String, StreamSinkChannel> connects = new HashMap<>();

    @PostConstruct
    void init() {
        log.info("启动SSE服务器");
        initHttpServer();
    }

    private void initHttpServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(5302, "0.0.0.0")
                .setHandler(new MessageCallback()).build();
        server.start();
    }

    @Override
    @Async
    public void Publish(Map<String, Object> config, Object payload) throws Exception {

        log.info("发布 ");

        var connect = connects.get(config.get("key"));

        //TODO json转换 为sse支持的格式

        var msg = "id: " + 1 + "\n";
        msg = msg + "event: pull\n";
        msg = msg + "retry: 10000\n";
        msg = msg + "data: " + payload + "\n\n";

        try {
            connect.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            System.out.println("推送异常" + config.get("key"));
            e.printStackTrace();
        }

    }
}
