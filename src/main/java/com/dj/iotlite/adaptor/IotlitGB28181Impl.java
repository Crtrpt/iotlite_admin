package com.dj.iotlite.adaptor;

import com.dj.iotlite.service.AdaptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class IotlitGB28181Impl implements Adaptor {
    
    @Autowired
    AdaptorService adaptorService;

    @Override
    public void install() {
        adaptorService.install("IOTLITE_GB28181",this.getClass().getSimpleName(),new HashMap<>());
    }

    @Override
    public void uninstall() {
        adaptorService.unInstall("IOTLITE_GB28181");
    }

}
