package com.dj.iotlite.adaptor.aliyunAdaptor;

import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.service.AdaptorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Getter
public class AliyunAdaptor implements Adaptor {

    @Autowired
    AdaptorService adaptorService;

    String name = "aliyun";

    @Override
    public void install() {
        adaptorService.install(getName(),this.getClass().getSimpleName(),new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }
}
