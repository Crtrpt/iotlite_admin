package com.dj.iotlite.datapush.ws;

import com.dj.iotlite.datapush.DataPush;
import com.dj.iotlite.datapush.sse.Sse1DataPushImpl;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xnio.channels.StreamSinkChannel;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.undertow.Handlers.*;

@Service
@Slf4j
public class WsDataPushImpl implements DataPush {

    @Data
    static
    class Context {
        Context(WebSocketChannel  ws){
            this.ws=ws;
        }
        WebSocketChannel  ws;
        AtomicInteger id=new AtomicInteger(1);
        HashMap<String,Object> ctx;
    }

    /**
     * 所有订阅者
     */
    static public Map<String, WsDataPushImpl.Context> connects = new HashMap<>();

    @Value("${app.push.ws.server.port}")
    int port;

    @PostConstruct
    void init() {
        log.info("启动ws服务器");
        initWsServer();
    }

    private void initWsServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(port, "0.0.0.0")
                .setHandler(path()
                        .addPrefixPath("/ws", websocket((exchange, channel) -> {
                            //判断订阅权限
                            connects.put(exchange.getRequestURI().substring(1),new Context(channel));
                            channel.getReceiveSetter().set(new AbstractReceiveListener() {
                                @Override
                                protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                    WebSockets.sendText(message.getData(), channel, null);
                                }
                            });
                            channel.resumeReceives();
                        })))
                .build();
        server.start();
    }

    @Override
    public void Publish(Map<String, Object> config, Object payload) throws Exception {
        var connect = connects.get(config.get("key"));
        WebSockets.sendText((String) payload, connect.getWs(), null);
    }
}
