package com.dj.iotlite.datapush.sse;


import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;
import org.xnio.channels.StreamSinkChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static io.undertow.Handlers.serverSentEvents;


class frame {

}

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


        final StreamSinkChannel sink = httpServerExchange.getResponseChannel();

        String key=httpServerExchange.getRequestURI();
        log.info("订阅 {}",key.substring(1));
        SseDataPushImpl.connects.put(key.substring(1),sink);

        var id = 0;
        while (true) {
            id=id+1;
            var msg="id: " + id + "\n";
            msg=msg+"event: test\n";
            msg=msg+"retry: 10000\n";
            msg=msg+"data: " + System.currentTimeMillis() + "\n\n";

            sink.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            Thread.sleep(5000);
            System.out.println("发送test"+msg);
        }

    }
}
