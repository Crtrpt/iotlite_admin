package com.dj.fusion.onenet;


import com.dj.fusion.onenet.client.OnenetClient;
import com.github.cm.heclouds.onenet.studio.api.IotClient;
import com.github.cm.heclouds.onenet.studio.api.entity.application.project.QueryProductListRequest;
import com.github.cm.heclouds.onenet.studio.api.exception.IotClientException;
import com.github.cm.heclouds.onenet.studio.api.exception.IotServerException;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.Properties;


class MainTest {
    @Test
    public void async() throws IOException, IotServerException, IotClientException {
        Properties prop = new Properties();
        prop.load(IotClient.class.getResourceAsStream("/onenet.platform.properties"));
        var client = OnenetClient.getClient(prop);
        QueryProductListRequest queryProductListRequest=new QueryProductListRequest();
        queryProductListRequest.setProjectId(prop.getProperty("user.projectId"));

       var response= client.sendRequest(queryProductListRequest);
        response.getList().stream().forEach(productInfo -> {
            System.out.println(productInfo.getName());
        });
    }

}