package com.dj.iotlite.service;

import com.dj.iotlite.entity.adaptor.Adapter;
import com.dj.iotlite.entity.repo.AdapterRepository;
import com.dj.iotlite.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class AdaptorServiceImpl implements AdaptorService {

    @Autowired
    AdapterRepository adapterRepository;

    @Override
    public void install(String name, String implName, HashMap<String, Object> meta) {
        adapterRepository.findFirstByName(name).ifPresent(a -> {
            throw new BusinessException("适配器已经存在");
        });
        var adaptor = new Adapter();
        adaptor.setName(name);
        adaptor.setImplClass(implName);
        adaptor.setMeta(meta);
        adapterRepository.save(adaptor);
    }

    @Override
    public void unInstall(String name) {
        adapterRepository.findFirstByName(name).ifPresentOrElse(adapter -> {
            adapterRepository.delete(adapter);
        },()->{
            throw new BusinessException("要删除的适配器不存在");
        });
    }
}
