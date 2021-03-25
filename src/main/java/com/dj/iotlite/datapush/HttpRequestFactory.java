package com.dj.iotlite.datapush;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.net.http.HttpRequest.newBuilder;

@Service
@Slf4j
public class HttpRequestFactory extends BaseKeyedPooledObjectFactory<Map<String, Object>, HttpRequest.Builder> {

    @Override
    public HttpRequest.Builder create(Map<String, Object> cfg) throws Exception {
        return HttpRequest.newBuilder()
                .uri(URI.create((String) cfg.get("url")))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json");
    }

    @Override
    public PooledObject<HttpRequest.Builder> wrap(HttpRequest.Builder obj) {
        return new DefaultPooledObject<>(obj);
    }
}
