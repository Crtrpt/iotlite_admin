package com.dj.iotlite.plugin.notice;

import com.dj.iotlite.plugin.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultNotice implements Notice {
    @Override
    public void send(Object target, Object content) {

    }
}
