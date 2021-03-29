package com.dj.iotlite.datapush.ws;



import com.dj.iotlite.datapush.sse.Sse1DataPushImpl;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import lombok.extern.slf4j.Slf4j;
import org.xnio.channels.StreamSinkChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;

@Slf4j
public class MessageCallback implements HttpHandler  {


    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {

    }
}
