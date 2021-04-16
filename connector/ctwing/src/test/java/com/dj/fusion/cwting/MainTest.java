package com.dj.fusion.cwting;

import com.ctg.ag.sdk.biz.aep_product_management.QueryProductListRequest;
import com.ctg.ag.sdk.biz.aep_product_management.QueryProductListResponse;
import com.dj.fusion.cwting.client.CtwingClient;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class MainTest {

    @Test
    public void async() throws Exception {
        Properties prop = new Properties();
        prop.load(CtwingClient.class.getResourceAsStream("/ctwing.platform.properties"));
        var client = CtwingClient.getProductClient(prop);

        var req = new QueryProductListRequest();

        QueryProductListResponse res = client.QueryProductList(req);

        System.out.println(new String(res.getBody()));

    }

}