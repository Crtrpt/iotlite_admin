package com.dj.iotlite.datapush.http;

import com.dj.iotlite.datapush.DataPush;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HttpDataPushImpl implements DataPush {

    KeyedObjectPool<Map<String, Object>, HttpClient> httpFactory = new GenericKeyedObjectPool<>(new HttpFactory());

    KeyedObjectPool<Map<String, Object>, HttpRequest.Builder> requestFactory = new GenericKeyedObjectPool<>(new HttpRequestFactory());
    /**
     * 默认的配置
     */
    HashMap<String, Object> defaultConfig = new HashMap<>();

    @Override
    @Async
    public void Publish(Map<String, Object> config, Object payload) throws Exception {
        var cfg = (HashMap<String, Object>) defaultConfig.clone();
        cfg.putAll(config);

        log.info("http push request");
        HttpClient client = httpFactory.borrowObject(config);

        var requestBuilder = requestFactory.borrowObject(config);

        var request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString((String) payload))
                .header("token", (String) config.get("token"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(s -> {
                    log.info("http push response {} ", s);
                    try {
                        /**
                         * 返还 http client对象
                         */
                        httpFactory.returnObject(config, client);
                        requestFactory.returnObject(config, requestBuilder);
                        System.out.println(httpFactory.getNumActive());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
