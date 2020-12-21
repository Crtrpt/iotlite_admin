package com.dj.iotlite.mqtt;

import io.moquette.broker.security.IAuthenticator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Authenticator implements IAuthenticator {
    @Override
    public boolean checkValid(String clientId, String username, byte[] password) {
        log.info("checkValid");
        return true;
    }
}