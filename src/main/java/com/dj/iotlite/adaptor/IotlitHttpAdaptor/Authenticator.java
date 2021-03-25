package com.dj.iotlite.adaptor.IotlitHttpAdaptor;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderValues;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Authenticator {
    public static boolean check(HttpServerExchange httpServerExchange) {
        return true;
    }

    public static boolean check(String requestURI, HeaderValues auth) {
        log.info("TODO 处理http协议授权认证");
        return true;
    }
}
