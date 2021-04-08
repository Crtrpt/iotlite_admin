package com.dj.fusion.cwting.client;


import com.ctg.ag.sdk.biz.AepDeviceManagementClient;
import com.ctg.ag.sdk.biz.AepProductManagementClient;

import java.util.Properties;

public class CtwingClient {

    public static AepProductManagementClient getProductClient(Properties prop) {
        return AepProductManagementClient.newClient().appKey(prop.getProperty("appKey")).appSecret(prop.getProperty("appSecret")).build();
    }

    public static AepDeviceManagementClient getDeviceClient(Properties prop) {
        return AepDeviceManagementClient.newClient().appKey(prop.getProperty("appKey")).appSecret(prop.getProperty("appSecret")).build();
    }
}


