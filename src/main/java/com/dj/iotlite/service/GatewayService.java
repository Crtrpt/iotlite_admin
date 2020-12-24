package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.GatewayTypeDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.gateway.Gateway;
import com.dj.iotlite.entity.gateway.GatewayRepository;
import com.dj.iotlite.entity.gateway.GatewayType;
import com.dj.iotlite.entity.gateway.GatewayTypeRepository;
import com.dj.iotlite.entity.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GatewayService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    GatewayRepository gatewayRepository;

    @Autowired
    GatewayTypeRepository gatewayTypeRepository;


    public Object queryDevice(String uuid) {
        return null;
    }

    public Page<Device> getDeviceList() {
        return null;
    }

    public Object removeDevice(String uuid) {
        return null;
    }

    public Object saveDevice() {
        return null;
    }

    public Object getProductList() {
        return null;
    }

    public Object removeProduct(String uuid) {
        return null;
    }

    public Object saveProduct() {
        return null;
    }

    public Object queryProduct(String uuid) {
        return null;
    }

    public Object removeGateway(String uuid) {
        return null;
    }

    public Object saveGateway() {
        return null;
    }

    public Object queryGateway(String uuid) {
        return null;
    }

    public Page<Gateway> getGatewayList(DeviceQueryForm query) {
        Specification<Gateway> specification = (Specification<Gateway>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return gatewayRepository.findAll(specification, query.getPage());
    }

    public Object queryAllGatewayType() {

        return gatewayTypeRepository.findAll().stream().map(s->{
            GatewayTypeDto target=new GatewayTypeDto();
            BeanUtils.copyProperties(s,target);
            return  target;
        }).collect(Collectors.toList());
    }
}
