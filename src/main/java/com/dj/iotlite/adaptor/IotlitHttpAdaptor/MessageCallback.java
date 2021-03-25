package com.dj.iotlite.adaptor.IotlitHttpAdaptor;

import com.dj.iotlite.adaptor.MessageScheduling;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageCallback implements HttpHandler, MessageScheduling {
    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        if (httpServerExchange.getRequestMethod().equals(Methods.POST)) {
            httpServerExchange.getRequestReceiver().receiveFullBytes((exchange1, bytes) -> {
                try {
                    dispatch(exchange1.getRequestURI(), bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            httpServerExchange.getResponseSender().send("please use post");
        }
    }
}
