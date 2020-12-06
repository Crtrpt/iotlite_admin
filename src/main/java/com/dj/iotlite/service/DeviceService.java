package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.DeviceDto;
import com.dj.iotlite.api.dto.OptionDto;
import com.dj.iotlite.api.dto.ProductDto;
import com.dj.iotlite.api.form.DeviceActionForm;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.ProductForm;
import com.dj.iotlite.api.form.ProductQueryForm;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceRepository;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.event.ChangeDevice;
import com.dj.iotlite.event.ChangeProduct;
import com.dj.iotlite.event.ChangeUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceService {

    @Autowired
    private ApplicationEventPublisher ep;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DeviceRepository deviceRepository;

    public Object queryDevice(Long id) {
        DeviceDto deviceDto=new DeviceDto();
        Device device= deviceRepository.findById(id).orElse(new Device());
        BeanUtils.copyProperties(device,deviceDto);

        ProductDto productDto=new ProductDto();
        Product product=productRepository.findById(device.getProductId()).orElse(new Product());
        BeanUtils.copyProperties(product,productDto);
        deviceDto.setProduct(productDto);
        return  deviceDto;
    }

    public Page<Device> getDeviceList(DeviceQueryForm deviceQueryForm) {
        Specification<Device> specification = (Specification<Device>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%")
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
        Device device = new Device();
        BeanUtils.copyProperties(deviceDto,device);
        deviceRepository.save(device);
        ep.publishEvent(new ChangeDevice(this,device,ChangeDevice.Action.ADD));
        return true;
    }

    public Page<Product> getProductList(ProductQueryForm query) {
        Specification<Product> specification = (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%"),
                        //账户
                        criteriaBuilder.like(root.get("account").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return productRepository.findAll(specification, query.getPage());
    }

    public Object removeProduct(String uuid) {
        return null;
    }

    public Object saveProduct(ProductForm productForm) {
        Product product = new Product();
        BeanUtils.copyProperties(productForm,product);
        productRepository.save(product);
        ep.publishEvent(new ChangeProduct(this,product,ChangeProduct.Action.ADD));
        return true;
    }

    public Object queryProduct(Long id) {
        ProductDto productDto=new ProductDto();
        Product product= productRepository.findById(id).orElse(new Product());
        BeanUtils.copyProperties(product,productDto);
        return  productDto;
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

    public void getProduct(Long productId, ProductDto productDto) {
       Product product= productRepository.findById(productId).orElse(new Product());
       BeanUtils.copyProperties(product,productDto);
    }

    public List<OptionDto> getAllProduct() {
        return productRepository.findAll().stream().map(p->{
            OptionDto optionDto=new OptionDto();
            optionDto.setId(p.getId());
            optionDto.setLabel(p.getName());
            return optionDto;
        }).collect(Collectors.toList());
    }
}
