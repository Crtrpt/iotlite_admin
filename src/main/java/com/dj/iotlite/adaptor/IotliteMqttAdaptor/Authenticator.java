package com.dj.iotlite.adaptor.IotliteMqttAdaptor;

import io.moquette.broker.security.IAuthenticator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Authenticator implements IAuthenticator {
    @Override
    public boolean checkValid(String clientId, String username, byte[] password) {
        log.info("TODO 处理设备授权 {} {} {} ",clientId,username,new String(password));
        return true;
    }
}
