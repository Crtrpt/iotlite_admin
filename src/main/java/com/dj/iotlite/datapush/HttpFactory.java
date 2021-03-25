package com.dj.iotlite.datapush;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HttpFactory extends BaseKeyedPooledObjectFactory<Map<String, Object>, HttpClient> {

    @Override
    public HttpClient create(Map config) throws Exception {
        return HttpClient.newHttpClient();
    }

    @Override
    public PooledObject<HttpClient> wrap(HttpClient httpClient) {
        return new DefaultPooledObject<>(httpClient);
    }
}
