package com.dj.fusion.onenet.client;


import com.github.cm.heclouds.onenet.studio.api.IotClient;
import com.github.cm.heclouds.onenet.studio.api.IotProfile;

import java.io.IOException;
import java.util.Properties;

public class OnenetClient {
    public static IotClient getClient(Properties prop) throws IOException {
        IotProfile profile = new IotProfile();
        profile.userId(prop.getProperty("user.clientId"))
                .accessKey(prop.getProperty("user.accesskey"));
        return IotClient.create(profile);
    }
}
