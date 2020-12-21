package com.dj.iotlite.mqtt;

import io.moquette.broker.ISslContextCreator;
import io.netty.handler.ssl.SslContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SslContextCreator implements ISslContextCreator {
    @Override
    public SslContext initSSLContext() {
        return null;
    }
}