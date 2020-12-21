package com.dj.iotlite.mqtt;

import io.moquette.broker.security.IAuthorizatorPolicy;
import io.moquette.broker.subscriptions.Topic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizatorPolicy implements IAuthorizatorPolicy {
    @Override
    public boolean canWrite(Topic topic, String username, String clientId) {

        return true;
    }

    @Override
    public boolean canRead(Topic topic, String username, String clientId) {
        return true;
    }
}