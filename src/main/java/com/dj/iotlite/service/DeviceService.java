package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.DeviceDto;
import com.dj.iotlite.api.form.DeviceActionForm;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceRepository;
import com.dj.iotlite.entity.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeviceService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DeviceRepository deviceRepository;

    public Object queryDevice(String uuid) {
        return null;
    }

    public Page<Device> getDeviceList(DeviceQueryForm deviceQueryForm) {
        Specification<Device> specification = (Specification<Device>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("remark").as(String.class), "%" + deviceQueryForm.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceRepository.findAll(specification, deviceQueryForm.getPage());
    }

    public Object removeDevice(String uuid) {
        return null;
    }

    public Object saveDevice(DeviceDto deviceDto) {
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

    /**
     * 对设备发送命令
     * @param action
     * @return
     */
    public Object action(DeviceActionForm action) {
        return null;
    }

    /**
     * 启用或者关闭设备
     * @param uuid
     * @return
     */
    public Object enable(String uuid) {
        return null;
    }
}
