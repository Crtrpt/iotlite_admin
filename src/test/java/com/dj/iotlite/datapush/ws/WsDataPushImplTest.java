package com.dj.iotlite.datapush.ws;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WsDataPushImplTest {

    @Test
    void init() throws IOException {
        var ws=new  WsDataPushImpl();
        ws.init();
        System.in.read();

    }
}