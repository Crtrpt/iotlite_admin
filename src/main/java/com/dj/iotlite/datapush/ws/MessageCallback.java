package com.dj.iotlite.datapush.ws;


import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MessageCallback implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws InterruptedException, IOException {

    }
}
