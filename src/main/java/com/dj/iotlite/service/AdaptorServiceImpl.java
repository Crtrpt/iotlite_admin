package com.dj.iotlite.service;

import com.dj.iotlite.entity.adaptor.Adaptor;
import com.dj.iotlite.entity.repo.AdaptorRepository;
import com.dj.iotlite.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class AdaptorServiceImpl implements AdaptorService {

    @Autowired
    AdaptorRepository adaptorRepository;

    @Override
    public void install(String name, String implName, HashMap<String, Object> meta) {
        adaptorRepository.findFirstByName(name).ifPresent(a -> {
            throw new BusinessException("适配器已经存在");
        });
        var adaptor = new Adaptor();
        adaptor.setName(name);
        adaptor.setImplClass(implName);
        adaptor.setMeta(meta);
        adaptorRepository.save(adaptor);
    }

    @Override
    public void unInstall(String name) {
        adaptorRepository.findFirstByName(name).ifPresentOrElse(adaptor -> {
            adaptorRepository.delete(adaptor);
        },()->{
            throw new BusinessException("要删除的适配器不存在");
        });
    }
}
