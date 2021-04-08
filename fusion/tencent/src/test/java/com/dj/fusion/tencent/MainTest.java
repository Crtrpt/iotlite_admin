package com.dj.fusion.tencent;

import com.dj.fusion.tencent.client.TencentClient;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iotcloud.v20180614.models.DescribeProductsRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


class MainTest {
    @Test
    public void aync() throws IOException {
        Properties prop = new Properties();
        prop.load(TencentClient.class.getResourceAsStream("/tencent.platform.properties"));
        var client = TencentClient.getClient(prop);
        try {
            var req = new DescribeProductsRequest();
            req.setOffset(1L);
            req.setLimit(10L);
            var resp = client.DescribeProducts(req);

            Arrays.stream(resp.getProducts()).forEach(p -> {
                System.out.println(p.getProductName());
            });
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}