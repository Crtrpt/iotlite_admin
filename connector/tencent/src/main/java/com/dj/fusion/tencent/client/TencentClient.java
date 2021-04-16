package com.dj.fusion.tencent.client;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iotcloud.v20180614.IotcloudClient;

;import java.io.IOException;
import java.util.Properties;

public class TencentClient {
    public static IotcloudClient getClient(Properties prop) throws IOException {
        Credential cred = new Credential(prop.getProperty("user.secretId"), prop.getProperty("user.secretKey"));


        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("iotcloud.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        IotcloudClient client = new IotcloudClient(cred, "", clientProfile);
        return  client;
    }
}
