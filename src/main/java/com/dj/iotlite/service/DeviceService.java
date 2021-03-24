package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceGroup;
import com.dj.iotlite.entity.device.DeviceGroupLink;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductRepository;
import com.dj.iotlite.entity.repo.DeviceGroupLinkRepository;
import com.dj.iotlite.entity.repo.DeviceGroupRepository;
import com.dj.iotlite.entity.repo.AdapterRepository;
import com.dj.iotlite.entity.repo.DeviceLogRepository;
import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.enums.DeviceCertEnum;
import com.dj.iotlite.enums.ProductDiscoverEnum;
import com.dj.iotlite.event.ChangeDevice;
import com.dj.iotlite.event.ChangeProduct;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.function.StateAble;
import com.dj.iotlite.mapper.DeviceMapper;
import com.dj.iotlite.spec.SpecV1;
import com.dj.iotlite.utils.UUID;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import io.lettuce.core.GeoArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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
    AdapterRepository adapterRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @Autowired
    DeviceGroupLinkRepository deviceGroupLinkRepository;

    public DeviceDto queryDevice(Long id) {
        DeviceDto deviceDto = new DeviceDto();
        Device device = deviceRepository.findById(id).orElse(new Device());
        BeanUtils.copyProperties(device, deviceDto);

        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(device.getProductId()).orElse(new Product());
        BeanUtils.copyProperties(product, productDto);
        deviceDto.setProduct(productDto);
        //设备当前的状态信息
        String member = String.format(RedisKey.DeviceLOCATION_MEMBER, device.getProductSn(), device.getSn());
        deviceDto.setSnap(redisCommands.hgetall(member));

        //上级代理设备
        if(!ObjectUtils.isEmpty(device.getProxyId())){
            deviceDto.setProxy(queryDevice(device.getProxyId()));
        }
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
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("tags").as(String.class), deviceQueryForm.getWords() + "%")
                ));
            }
            /**
             * 获取全部的子设备
             */
            if (!ObjectUtils.isEmpty(deviceQueryForm.getProxyId())) {
                list.add(criteriaBuilder.equal(root.get("proxyId").as(Long.class), deviceQueryForm.getProxyId()));
            }
            /**
             * 获取指定产品下面的全部设备
             */
            if (!StringUtils.isEmpty(deviceQueryForm.getProductSn())) {
                list.add(criteriaBuilder.equal(root.get("productSn").as(String.class), deviceQueryForm.getProductSn()));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceRepository.findAll(specification, deviceQueryForm.getPage());
    }

    @Transactional(rollbackFor = Exception.class)
    public Object removeDevice(DeviceRemoveForm form) {
        deviceRepository.deleteById(form.getId());
        var links = deviceGroupLinkRepository.findAllByProductSnAndDeviceSn(form.getProductSn(), form.getDeviceSn());
        deviceGroupLinkRepository.deleteInBatch(links);
        //TODO 清除其他的状态
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object saveDevice(DeviceSaveForm deviceDto) {
        Device device = new Device();
        BeanUtils.copyProperties(deviceDto, device);
        device.setVer(1);
        device.setCreatedAt(System.currentTimeMillis());
        productRepository.findById(deviceDto.getProductId()).ifPresentOrElse(p -> {
            device.setSpec(p.getSpec());
            device.setProductSn(p.getSn());
            device.setTags(new ArrayList<>());
            //创建的时候的版本
            device.setVersion(p.getVersion());
        }, () -> {
            throw new BusinessException("设备不存在");
        });

        var groupIdsMap = new HashMap<String, Long>();
        if (!ObjectUtils.isEmpty(deviceDto.getDeviceGroup())) {
            Arrays.stream(deviceDto.getDeviceGroup().split(",")).forEach((s) -> {
                if (s.equals("")) {
                } else {
                    deviceGroupRepository.findFirstByName(s).ifPresentOrElse(
                            (s1) -> {
                                groupIdsMap.put(s1.getName(), s1.getId());
                            }
                            , () -> {
                                var deviceGroup = new com.dj.iotlite.entity.device.DeviceGroup();
                                deviceGroup.setName(s);
                                deviceGroup.setDescription(s);
                                var s2 = deviceGroupRepository.save(deviceGroup);
                                groupIdsMap.put(s2.getName(), s2.getId());
                            });
                }
            });
        }

        batchCopy(device, deviceDto, groupIdsMap);
        deviceRepository.save(device);
        //保存分组关系
        for (Map.Entry<String, Long> entry : groupIdsMap.entrySet()) {
            String name = entry.getKey();
            Long id = entry.getValue();
            var link = new DeviceGroupLink();
            link.setDeviceSn(device.getSn());
            link.setProductSn(device.getProductSn());
            link.setGroupId(id);
            link.setGroupName(name);
            link.setDeviceId(device.getId());
            deviceGroupLinkRepository.save(link);
        }
        if (!ObjectUtils.isEmpty(deviceDto.getDeviceGroup())) {
            //缓存设备的设备组信息
            var deviceKey = String.format(RedisKey.DEVICE, device.getProductSn(), deviceDto.getSn());
            redisCommands.hset(deviceKey, "deviceGroup", deviceDto.getDeviceGroup());
        }

        ep.publishEvent(new ChangeDevice(this, device, ChangeDevice.Action.ADD, groupIdsMap));
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
                link.setProductSn(d.getProductSn());
                link.setDeviceId(d.getId());
                deviceGroupLinkRepository.save(link);
            }
            //缓存每个涉笔的设备组信息
            if (!ObjectUtils.isEmpty(deviceDto.getDeviceGroup())) {
                var deviceKey = String.format(RedisKey.DEVICE, device.getProductSn(), d.getSn());
                redisCommands.hset(deviceKey, "deviceGroup", deviceDto.getDeviceGroup());
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

    @Transactional(rollbackFor = Exception.class)
    public Object removeProduct(ProductRemoveForm form) {
        productRepository.findById(form.getId()).ifPresentOrElse((p) -> {
            //删除所有设备
            deviceRepository.deleteAll(deviceRepository.findAllByProductSn(p.getSn()));
            //删除组内关系
            var links = deviceGroupLinkRepository.findAllByProductSn(p.getSn());
            deviceGroupLinkRepository.deleteInBatch(links);
            //删除产品
            productRepository.delete(p);
            // TODO 清理各种状态
        }, () -> {
            throw new BusinessException("产品已经不存在");
        });
        return true;
    }

    public Object saveProduct(ProductForm productForm) {
        Product product = new Product();
        BeanUtils.copyProperties(productForm, product);
        product.setSpec("{}");
        product.setTags("[]");
        product.setVersion("0.0.0");
        product.setSn(UUID.getUUID());
        product.setDeviceCert(DeviceCertEnum.none);
        product.setSecKey(UUID.getUUID());
        product.setDiscover(ProductDiscoverEnum.auto);
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
            optionDto.setSn(p.getSn());
            return optionDto;
        }).collect(Collectors.toList());
    }

    public List<AdapterOptionDto> getAllAdapterOptionDto() {
        return adapterRepository.findAll().stream().map(p -> {
            AdapterOptionDto optionDto = new AdapterOptionDto();
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

        deviceLocationDto.setLocation(redisCommands.geopos(key, member).get(0));
        if (redisCommands.geopos(key, member).size() > 0) {
            var center = redisCommands.geopos(key, member).get(0);
            if (!ObjectUtils.isEmpty(center)) {
                //获取附近的
                var arg = new GeoArgs();
                arg.withCoordinates();
                arg.withDistance();
                deviceLocationDto.setNearby(redisCommands.georadius(key, center.getX().doubleValue(), center.getY().doubleValue(), Integer.valueOf(action.getRadius()), GeoArgs.Unit.km, arg));
            }
        }

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

    public DeviceGroupDto queryDeviceGroup(Long id) {
        var deviceGroupDto = new DeviceGroupDto();
        DeviceGroup device = deviceGroupRepository.findById(id).orElse(new DeviceGroup());
        BeanUtils.copyProperties(device, deviceGroupDto);
        Script s = GroupInstanceImpl.groupScriptMapping.get(device.getName());
        if (ObjectUtils.isEmpty(s)) {
            deviceGroupDto.setState(new HashMap<>());
        } else {
            deviceGroupDto.setState(((StateAble) s.getProperty("state")).getStates());
        }

        return deviceGroupDto;
    }

    @Autowired(required = false)
    DeviceMapper deviceMapper;

    /**
     * 获取组内设备列表
     *
     * @param deviceQueryForm
     * @return
     */
    public com.dj.iotlite.api.dto.Page<DeviceListDto> getGroupDeviceList(DeviceQueryForm deviceQueryForm) {
        Gson gson = new Gson();

        var ret = new com.dj.iotlite.api.dto.Page<DeviceListDto>();
        List<DeviceListDto> list = new ArrayList<>();
        PageHelper.startPage(1, 10);

        var data = deviceMapper.getGroupDeviceList(deviceQueryForm);

        data.stream().forEach(d -> {
            var t = new DeviceListDto();
            BeanUtils.copyProperties(d, t);
            var pto = new ProductDto();
            var p = productRepository.findFirstBySn(d.getProductSn()).orElse(new Product());
            BeanUtils.copyProperties(p, pto);
            t.setProduct(pto);
            t.setTags(gson.fromJson(String.valueOf(d.getTags()), List.class));
            list.add(t);
        });
        ret.setList(list);
        ret.setTotal(data.getTotal());
        return ret;
    }

    public Object queryDeviceGroup(String name) {
        var deviceGroupDto = new DeviceGroupDto();
        DeviceGroup device = deviceGroupRepository.findFirstByName(name).orElse(new DeviceGroup());
        BeanUtils.copyProperties(device, deviceGroupDto);
        return deviceGroupDto;
    }

    @Async
    public void refreshGroupDeviceCount(Collection<Long> values) {
        values.stream().forEach(id -> {
            deviceGroupRepository.findById(id).ifPresent(
                    d -> {
                        d.setDeviceCount(deviceGroupLinkRepository.countByGroupId(id));
                        deviceGroupRepository.save(d);
                    }
            );
        });
    }

    public Object saveGroupFence(DeviceGroupFenceSaveForm form) {
        deviceGroupRepository.findById(form.getId()).ifPresent(d -> {
            d.setFence(form.getFence());
            deviceGroupRepository.save(d);
        });
        return true;
    }

    public Object saveGroupPlayground(DeviceGroupSpecSaveForm form) {
        deviceGroupRepository.findById(form.getId()).ifPresent(d -> {
            d.setSpec(form.getSpec());

            //编排更新保持状态
            var state = GroupInstanceImpl.groupScriptMapping.get(d.getName()).getProperty("state");
            GroovyShell gs = new GroovyShell();
            Script script = gs.parse(form.getSpec());
            script.setProperty("state", state);
            GroupInstanceImpl.groupScriptMapping.put(d.getName(), script);

            deviceGroupRepository.save(d);
            //编排文件存入redis
            var deviceKey = String.format(RedisKey.DEVICE_GROUP, d.getName());
            redisCommands.hset(deviceKey, "playground", form.getSpec());
        });
        return true;
    }

    public Object setDeviceMeta(DeviceMetaForm form) {
        deviceRepository.findFirstBySnAndProductSn(form.getDeviceSn(), form.getProductSn()).ifPresent(d -> {
            var meta = d.getMeta();
            var hashMeta = new HashMap<String, Object>();
            if (!ObjectUtils.isEmpty(meta)) {
                hashMeta = (HashMap<String, Object>) meta;
            }
            hashMeta.put(form.getKey(), form.getValue());
            d.setMeta(hashMeta);
            deviceRepository.save(d);
        });
        return true;
    }

    /**
     * TODO 修改为只显示视口内的设备
     *
     * @param query
     * @return
     */
    public Object getMapDeviceList(MapDeviceQueryForm query) {
        List<DeviceMapLocationDto> ret = new ArrayList<>();
        String key = String.format(RedisKey.DeviceLOCATION, "default");
        deviceRepository.findAllByProductSn(query.getProductSn()).stream().forEach(s -> {
            DeviceMapLocationDto t = new DeviceMapLocationDto();
            String member = String.format(RedisKey.DeviceLOCATION_MEMBER, s.getProductSn(), s.getSn());
            BeanUtils.copyProperties(s, t);
            t.setDeviceSn(s.getSn());
            t.setProductSn(s.getProductSn());
            t.setLocation(redisCommands.geopos(key, member).get(0));
            ret.add(t);
        });
        return ret;
    }

    public Object groupStateClean(DeviceGroupCleanForm deviceGroupCleanForm) {
        deviceGroupRepository.findById(deviceGroupCleanForm.getId()).ifPresent(g -> {
            var s = GroupInstanceImpl.groupScriptMapping.get(g.getName());
            if (s != null) {
                ((StateAble) s.getProperty("state")).cleanAll();
            }
        });
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object groupRemove(DeviceGroupRemoveForm groupRemoveForm) {
        deviceGroupRepository.deleteById(groupRemoveForm.getId());
        deviceGroupLinkRepository.deleteInBatch(deviceGroupLinkRepository.findAllByGroupId(groupRemoveForm.getId()));
        return true;
    }
}
