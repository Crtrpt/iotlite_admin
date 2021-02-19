package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.*;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.event.ChangeDevice;
import com.dj.iotlite.event.ChangeProduct;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.mapper.DeviceMapper;
import com.dj.iotlite.spec.SpecV1;
import com.dj.iotlite.utils.UUID;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
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

    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @Autowired
    DeviceGroupLinkRepository deviceGroupLinkRepository;

    public Object queryDevice(Long id) {
        DeviceDto deviceDto = new DeviceDto();
        Device device = deviceRepository.findById(id).orElse(new Device());
        BeanUtils.copyProperties(device, deviceDto);

        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(device.getProductId()).orElse(new Product());
        BeanUtils.copyProperties(product, productDto);
        deviceDto.setProduct(productDto);
        return deviceDto;
    }

    public Page<Device> getDeviceList(DeviceQueryForm deviceQueryForm) {
        Specification<Device> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("sn").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("ver").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("tags").as(String.class), deviceQueryForm.getWords() + "%")
                ));
            }
            if (!StringUtils.isEmpty(deviceQueryForm.getProductSn())) {
                list.add(criteriaBuilder.equal(root.get("productSn").as(String.class), deviceQueryForm.getProductSn()));
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

    @Transactional(rollbackFor = Exception.class)
    public Object saveDevice(DeviceSaveForm deviceDto) {
        Device device = new Device();
        BeanUtils.copyProperties(deviceDto, device);
        device.setVersion(1);
        device.setCreatedAt(System.currentTimeMillis());
        productRepository.findById(deviceDto.getProductId()).ifPresentOrElse(p -> {
            device.setSpec(p.getSpec());
            device.setProductSn(p.getSn());
            device.setTags(new ArrayList<>());
            //创建的时候的版本
            device.setVer(p.getVer());
        }, () -> {
            throw new BusinessException("设备不存在");
        });

        var groupIdsMap=new HashMap<String,Long>();
        Arrays.stream(deviceDto.getDeviceGroup().split(",")).forEach((s)->{
            if(s.equals("")){
            }else {
                deviceGroupRepository.findFirstByName(s).ifPresentOrElse(
                        (s1)->{
                            groupIdsMap.put(s1.getName(),s1.getId());
                        }
                        ,()->{
                            var deviceGroup=new DeviceGroup();
                            deviceGroup.setName(s);
                            deviceGroup.setDescription(s);
                            var s2=deviceGroupRepository.save(deviceGroup);
                            groupIdsMap.put(s2.getName(),s2.getId());
                        });
            }
        });

        batchCopy(device, deviceDto,groupIdsMap);
        deviceRepository.save(device);
        //保存分组关系
        for (Map.Entry<String, Long> entry : groupIdsMap.entrySet()) {
            String name = entry.getKey();
            Long id = entry.getValue();
            var link = new DeviceGroupLink();
            link.setDeviceSn(device.getSn());
            link.setGroupId(id);
            link.setGroupName(name);
            deviceGroupLinkRepository.save(link);
        }

        ep.publishEvent(new ChangeDevice(this, device, ChangeDevice.Action.ADD));
        return true;
    }

    public List<Device> batchCopy(Device device, DeviceSaveForm deviceDto, HashMap<String, Long> groupIdsMap) {
        var sn = deviceDto.getSn();
        //去掉数字
        String prefix = com.dj.iotlite.utils.StringUtils.parseStr(deviceDto.getSn());
        Long number = com.dj.iotlite.utils.StringUtils.parseNumber(deviceDto.getSn());
        Integer padinglength = com.dj.iotlite.utils.StringUtils.parsePadding(deviceDto.getSn());

        Long maxCount = Long.parseLong(String.format("%1$" + padinglength + "s", 9).replace(' ', '9'));

        if (deviceDto.getCount() > maxCount) {
            throw new BusinessException("数字掩码错误 maxCount:" + maxCount);
        }
        var list = Collections.nCopies(deviceDto.getCount() - 1, device);
        for (Device d : list) {
            Device deviceCopy = new Device();
            number = number + 1;
            BeanUtils.copyProperties(d, deviceCopy, "id");
            if (padinglength > 0) {
                deviceCopy.setSn(String.format(prefix + "%1$" + padinglength + "s", number).replace(' ', '0'));
            } else {
                deviceCopy.setSn(prefix + number);
            }
            log.info("批量创建设备" + d.getSn());
            d = deviceCopy;
            deviceRepository.save(d);

            //保存分组关系
            for (Map.Entry<String, Long> entry : groupIdsMap.entrySet()) {
                String name = entry.getKey();
                Long id = entry.getValue();
                var link = new DeviceGroupLink();
                link.setDeviceSn(d.getSn());
                link.setGroupId(id);
                link.setGroupName(name);
                deviceGroupLinkRepository.save(link);
            }
        }
        log.info("批量创建设备" + list.size());
        return list;
    }

    public Page<Product> getProductList(ProductQueryForm query) {
        Specification<Product> specification = (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        criteriaBuilder.like(root.get("sn").as(String.class), "%" + query.getWords() + "%"),
                        criteriaBuilder.like(root.get("ver").as(String.class), "%" + query.getWords() + "%"),
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
        return productRepository.findAll(specification, query.getPage());
    }

    public Object removeProduct(String uuid) {
        return null;
    }

    public Object saveProduct(ProductForm productForm) {
        Product product = new Product();
        BeanUtils.copyProperties(productForm, product);
        product.setSpec(new Object());
        productRepository.save(product);
        ep.publishEvent(new ChangeProduct(this, product, ChangeProduct.Action.ADD));
        return true;
    }

    public Object queryProduct(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("设备不存在");
        });
        BeanUtils.copyProperties(product, productDto);
        productDto.setDeviceCount(deviceRepository.countByProductSn(product.getSn()));
        return productDto;
    }

    @Autowired
    DeviceInstance deviceInstance;

    /**
     * 对设备发送命令
     *
     * @param action
     * @return
     */
    public Object action(DeviceActionForm action) {
        deviceRepository.findFirstBySnAndProductSn(action.getDeviceSn(), action.getProductSn()).ifPresentOrElse((d) -> {
            var oldSpec = d.getSpec();
            SpecV1 specV1 = new SpecV1();
            Gson gson = new Gson();
            try {
                specV1.fromJson(gson.toJson(d.getSpec()));
                specV1.action(action.getName());
                HashMap<String, Object> propertys = new HashMap<>();
                specV1.getProperty().forEach(p -> {
                    if (!p.getValue().equals(p.getExpect())) {
                        propertys.put(p.getName(), p.getExpect());
                    }
                });
                deviceInstance.setPropertys(action.getProductSn(), action.getDeviceSn(), propertys, action.getName());
            } catch (Exception e) {
                throw new BusinessException("设备物模型解析异常");
            }
        }, () -> {
            throw new BusinessException("设备不存在");
        });
        return null;
    }

    /**
     * 启用或者关闭设备
     *
     * @param uuid
     * @return
     */
    public Object enable(String uuid) {
        return null;
    }

    public void getProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElse(new Product());
        BeanUtils.copyProperties(product, productDto);
    }

    public List<OptionDto> getAllProduct() {
        return productRepository.findAll().stream().map(p -> {
            OptionDto optionDto = new OptionDto();
            optionDto.setId(p.getId());
            optionDto.setLabel(p.getName());
            return optionDto;
        }).collect(Collectors.toList());
    }

    @Autowired
    DeviceLogRepository deviceLogRepository;

    public Page<DeviceLog> getDeviceLogList(DeviceLogQueryForm deviceQueryForm) {
        Specification<DeviceLog> specification = (Specification<DeviceLog>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("source").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("target").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        //
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%")
                ));

            }
            list.add(criteriaBuilder.equal(root.get("deviceSn").as(String.class), deviceQueryForm.getDeviceSn()));
            list.add(criteriaBuilder.equal(root.get("productSn").as(String.class), deviceQueryForm.getProductSn()));
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceLogRepository.findAll(specification, deviceQueryForm.getPage());
    }

    @Autowired
    RedisCommands<String, String> redisCommands;

    public Object location(DeviceLocationForm action) {
        String key = String.format(RedisKey.DeviceLOCATION, "default");
        String member = String.format(RedisKey.DeviceLOCATION_MEMBER, action.getProductSn(), action.getDeviceSn());

        log.info("{} {} ", key, member);

        DeviceLocationDto deviceLocationDto = new DeviceLocationDto();

        deviceLocationDto.setLocation(redisCommands.geopos(key, member));

        return deviceLocationDto;
    }

    public Object saveModel(ProductSaveModelForm form) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        Gson gson = new Gson();
        product.setSpec(gson.fromJson(form.getSpec(), Object.class));
        productRepository.save(product);
        return true;
    }

    public Object refreshProductKey(ProductRefreshProductKeyForm form) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        product.setSecKey(UUID.getUUID());
        productRepository.save(product);
        return product.getSecKey();
    }

    public Object refreshDeviceKey(DeviceRefreshDeviceKeyForm form) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });

        var device = deviceRepository.findFirstBySnAndProductSn(form.getDeviceSn(), form.getProductSn()).orElseThrow(
                () -> {
                    throw new BusinessException("设备不存在或者已经被删除");
                }
        );
        device.setDeviceKey(UUID.getUUID());
        deviceRepository.save(device);
        return device.getDeviceKey();
    }

    public Object changeTags(ChangeTagsForm form) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        product.setTags(form.getTags());
        productRepository.save(product);
        return true;
    }

    public Object deviceChangeTags(DeviceChangeTagsForm form) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        var device = deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).orElseThrow(
                () -> {
                    throw new BusinessException("设备不存在或者已经被删除");
                }
        );
        device.setTags((form.getTags()));
        deviceRepository.save(device);
        return true;
    }

    public Object deviceGroupSave(DeviceGroupSaveForm deviceDto) {
        var deviceGroup = new DeviceGroup();
        deviceGroupRepository.findFirstByName(deviceDto.getName()).ifPresentOrElse((e) -> {
            throw new BusinessException("设备分组已经存在");
        }, () -> {
            BeanUtils.copyProperties(deviceDto, deviceGroup);
            deviceGroup.setCreatedAt(System.currentTimeMillis());
            deviceGroupRepository.save(deviceGroup);
        });
        return deviceGroup;
    }

    public Page<DeviceGroup> getDeviceGroupList(DeviceQueryForm deviceQueryForm) {
        Specification<DeviceGroup> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%")
                ));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceGroupRepository.findAll(specification, deviceQueryForm.getPage());
    }

    public Object queryDeviceGroup(Long id) {
        var deviceGroupDto = new DeviceGroupDto();
        DeviceGroup device = deviceGroupRepository.findById(id).orElse(new DeviceGroup());
        BeanUtils.copyProperties(device, deviceGroupDto);
        BeanUtils.copyProperties(device, deviceGroupDto);
        return deviceGroupDto;
    }

    @Autowired(required = false)
    DeviceMapper deviceMapper;

    /**
     * 获取组内设备列表
     * @param deviceQueryForm
     * @return
     */
    public com.dj.iotlite.api.dto.Page<DeviceListDto> getGroupDeviceList(DeviceQueryForm deviceQueryForm) {
        var ret=new com.dj.iotlite.api.dto.Page<DeviceListDto>();
        List<DeviceListDto> list=new ArrayList<>();
        PageHelper.startPage(1, 10);

        var data=  deviceMapper.getGroupDeviceList(deviceQueryForm);

        System.out.println(data);

        data.stream().forEach(d->{
            var t=new DeviceListDto();
            BeanUtils.copyProperties(d,t);
            list.add(t);
        });
        ret.setList(list);
        ret.setTotal(data.getTotal());
        return ret;
    }
}
