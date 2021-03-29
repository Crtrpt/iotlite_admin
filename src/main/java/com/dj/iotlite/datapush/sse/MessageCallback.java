package com.dj.iotlite.datapush.sse;


import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;
import org.xnio.channels.StreamSinkChannel;

import java.io.IOException;

@Slf4j
public class MessageCallback implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws InterruptedException, IOException {
        //TODO 判断token 是否有权限订阅
        httpServerExchange.getResponseHeaders()
                .put(Headers.CONNECTION, "keep-alive")
                .put(Headers.CONTENT_TYPE, "text/event-stream")
                .put(Headers.CACHE_CONTROL, "no-cache")
                .put(HttpString.tryFromString("Access-Control-Allow-Origin"), "*");
        httpServerExchange.setPersistent(false);
        final StreamSinkChannel sink = httpServerExchange.getResponseChannel();
        String key = httpServerExchange.getRequestURI();
        log.info("订阅 {}", key.substring(1));
        Sse1DataPushImpl.connects.put(key.substring(1), new Sse1DataPushImpl.Context(sink));
        sink.resumeWrites();
    }
}
