package com.dj.iotlite.adaptor.IotliteGB28181Adaptor;

import com.dj.iotlite.adaptor.Adaptor;
import com.dj.iotlite.service.AdaptorService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Getter
public class IotlitGB28181Impl implements Adaptor {

    String name = "IOTLITE_GB28181";

    @Autowired
    AdaptorService adaptorService;

    @Override
    public void install() {
        adaptorService.install(getName(),this.getClass().getSimpleName(),new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall(getName());
    }

}
