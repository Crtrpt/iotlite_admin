package com.dj.iotlite.datapush.sse;

import com.dj.iotlite.datapush.DataPush;
import io.undertow.Undertow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;


@Service
@Slf4j
public class SSEDataPushImpl implements DataPush {

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

    }
}
