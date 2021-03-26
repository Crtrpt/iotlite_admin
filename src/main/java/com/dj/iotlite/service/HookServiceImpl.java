package com.dj.iotlite.service;

import com.dj.iotlite.api.form.HookSaveForm;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HookServiceImpl implements HookService {

    @Autowired
    RedisCommands<String, String> redisCommands;

    @Override
    public Object save(HookSaveForm form) {
        redisCommands.hmset(form.getKey(),form.getData());
        return null;
    }
}
