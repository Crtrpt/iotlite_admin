package com.dj.iotlite.service;

import com.dj.iotlite.RedisKey;
import com.dj.iotlite.adaptor.IotliteMqttAdaptor.RegisterDto;
import com.dj.iotlite.api.dto.*;
import com.dj.iotlite.api.form.*;
import com.dj.iotlite.entity.adaptor.Adapter;
import com.dj.iotlite.entity.device.Device;
import com.dj.iotlite.entity.device.DeviceGroup;
import com.dj.iotlite.entity.device.DeviceGroupLink;
import com.dj.iotlite.entity.device.DeviceLog;
import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.entity.repo.*;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.enums.*;
import com.dj.iotlite.event.ChangeDevice;
import com.dj.iotlite.event.ChangeProduct;
import com.dj.iotlite.exception.BusinessException;
import com.dj.iotlite.function.StateAble;
import com.dj.iotlite.mapper.DeviceMapper;
import com.dj.iotlite.spec.SpecV1;
import com.dj.iotlite.utils.JsonUtils;
import com.dj.iotlite.utils.UUID;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import io.lettuce.core.GeoArgs;
import io.lettuce.core.api.sync.RedisCommands;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    ProductVersionRepository productVersionRepository;

    @Autowired
    AdapterRepository adapterRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceGroupRepository deviceGroupRepository;

    @Autowired
    DeviceGroupLinkRepository deviceGroupLinkRepository;

    void checkUserCanAccessDevice(Device device, User user) {
        if (!device.getOwner().equals(user.getId())) {
            throw new BusinessException("device access denied");
        }
    }

    void checkUserCanAccessProduct(Product product, User user) {
        if (!product.getOwner().equals(user.getId())) {
            throw new BusinessException("device access denied");
        }
    }

    void checkUserCanAccessGroup(DeviceGroup group, User user) {
        if (!group.getOwner().equals(user.getId())) {
            throw new BusinessException("device access denied");
        }
    }

    public DeviceDto queryDevice(Long id, User userInfo) {
        DeviceDto deviceDto = new DeviceDto();
        Device device = deviceRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("not found device");
        });
        checkUserCanAccessDevice(device, userInfo);

        BeanUtils.copyProperties(device, deviceDto);

        ProductDto productDto = new ProductDto();
        ProductVersion product = productVersionRepository.findFirstBySnAndVersion(device.getProductSn(), device.getVersion()).orElseThrow(() -> {
            throw new BusinessException("not found product");
        });
        BeanUtils.copyProperties(product, productDto);
        deviceDto.setProduct(productDto);
        //设备当前的状态信息
        String member = String.format(RedisKey.DeviceLOCATION_MEMBER, device.getProductSn(), device.getSn());
        deviceDto.setSnap(redisCommands.hgetall(member));

        //上级代理设备
        if (!ObjectUtils.isEmpty(device.getProxyId())) {
            deviceDto.setProxy(queryDevice(device.getProxyId(), userInfo));
        }
        var key = "hook@device@" + member;
        deviceDto.setHook(redisCommands.hgetall(key));
        return deviceDto;
    }

    public Page<Device> getDeviceList(DeviceQueryForm deviceQueryForm, User userInfo) {
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

            /**
             * 获取指定产品下面的全部设备
             */
            if (!StringUtils.isEmpty(deviceQueryForm.getVersion())) {
                list.add(criteriaBuilder.equal(root.get("version").as(String.class), deviceQueryForm.getVersion()));
            }

            list.add(criteriaBuilder.equal(root.get("owner").as(Long.class), userInfo.getId()));

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceRepository.findAll(specification, deviceQueryForm.getPage());
    }

    @Transactional(rollbackFor = Exception.class)
    public Object removeDevice(DeviceRemoveForm form, User userInfo) {
        checkUserCanAccessDevice(deviceRepository.findById(form.getId()).get(), userInfo);
        deviceRepository.deleteById(form.getId());
        var links = deviceGroupLinkRepository.findAllByProductSnAndDeviceSn(form.getProductSn(), form.getDeviceSn());
        deviceGroupLinkRepository.deleteInBatch(links);
        //TODO 清除其他的状态
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object saveDevice(DeviceSaveForm form, User userInfo) {
        Device device = new Device();
        BeanUtils.copyProperties(form, device);
        device.setVer(1);
        device.setCreatedAt(System.currentTimeMillis());
        device.setTags(new ArrayList<>());
        device.setAccess(form.getAccess());
        device.setHdVersion(form.getHdVersion());
        //创建的时候的版本
        device.setVersion(form.getVersion());
        device.setOwner(userInfo.getId());

        deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).ifPresent(d -> {
            throw new BusinessException("device already exists ");
        });

        productVersionRepository.findFirstBySnAndVersion(form.getProductSn(), form.getVersion()).ifPresentOrElse(pv -> {
            device.setSpec(pv.getSpec());
            device.setProductSn(pv.getSn());
            device.setReleaseType(pv.getReleaseType());
        }, () -> {
            throw new BusinessException("product version not found");
        });

        var groupIdsMap = new HashMap<String, Long>();
        if (!ObjectUtils.isEmpty(form.getDeviceGroup())) {
            Arrays.stream(form.getDeviceGroup().split(",")).forEach((s) -> {
                if (!s.equals("")) {
                    deviceGroupRepository.findFirstByName(s).ifPresentOrElse(
                            (s1) -> {
                                groupIdsMap.put(s1.getName(), s1.getId());
                            }
                            , () -> {
                                var deviceGroup = new com.dj.iotlite.entity.device.DeviceGroup();
                                deviceGroup.setName(s);
                                deviceGroup.setDescription(s);
                                deviceGroup.setOwner(userInfo.getId());
                                var s2 = deviceGroupRepository.save(deviceGroup);
                                groupIdsMap.put(s2.getName(), s2.getId());
                            });
                }
            });
        }
        if (!ObjectUtils.isEmpty(form.getCount())) {
            batchCopy(device, form, groupIdsMap);
        }

        deviceRepository.save(device);
        //保存分组关系
        for (Map.Entry<String, Long> entry : groupIdsMap.entrySet()) {
            String name = entry.getKey();
            Long id = entry.getValue();
            var link = new DeviceGroupLink();
            link.setDeviceSn(device.getSn());
            link.setProductSn(device.getProductSn());
            link.setVersion(device.getVersion());
            link.setGroupId(id);
            link.setGroupName(name);
            link.setDeviceId(device.getId());
            deviceGroupLinkRepository.save(link);
        }
        if (!ObjectUtils.isEmpty(form.getDeviceGroup())) {
            //缓存设备的设备组信息
            var deviceKey = String.format(RedisKey.DEVICE, device.getProductSn(), form.getSn());
            redisCommands.hset(deviceKey, "deviceGroup", form.getDeviceGroup());
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
                link.setVersion(d.getVersion());
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


    public Page<Product> getProductList(ProductQueryForm query, User userInfo) {
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
            //获取我的产品列表
            list.add(criteriaBuilder.equal(root.get("owner").as(Long.class), userInfo.getId()));

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return productRepository.findAll(specification, query.getPage());
    }

    @Transactional(rollbackFor = Exception.class)
    public Object removeProduct(ProductRemoveForm form, User userInfo) {
        productRepository.findById(form.getId()).ifPresentOrElse((p) -> {
            checkUserCanAccessProduct(p,userInfo);
            //删除所有所有版本的产品
            productVersionRepository.deleteAll(productVersionRepository.findAllBySn(p.getSn()));
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

    @Transactional(rollbackFor = Exception.class)
    public Object saveProduct(ProductForm productForm, User userInfo) {
        Product product = new Product();
        BeanUtils.copyProperties(productForm, product);
        product.setSpec("{}");
        product.setTags("[]");
        product.setMeta("{}");
        product.setVersion("0.0.0");
        product.setSn(UUID.getUUID());
        product.setDeviceCert(productForm.getCert());
        product.setSecKey(UUID.getUUID());
        product.setDiscover(productForm.getDiscover());
        product.setAccess(productForm.getAccess());
        product.setOwner(userInfo.getId());
        //自动更新
        product.setUpdateStrategy(UpdateStrategyEnum.auto);
        product.setInterpreter(InterpreterTypeEnum.GROOVY);
        productRepository.save(product);
//        ep.publishEvent(new ChangeProduct(this, product, ChangeProduct.Action.ADD));
        return true;
    }

    public Object queryProduct(String sn, User userInfo) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findFirstBySn(sn).orElseThrow(() -> {
            throw new BusinessException("not found device");
        });
        checkUserCanAccessProduct(product,userInfo);
        BeanUtils.copyProperties(product, productDto);
        productDto.setDeviceCount(deviceRepository.countByProductSn(product.getSn()));
        return productDto;
    }

    @Autowired
    DeviceInstance deviceInstance;


    /**
     * TODO 优化为每个产品一个spec 或者 池化对象
     *
     * @param action
     * @return
     */
    @Cacheable(value = "device_spec", key = "#{action.productSn}")
    public com.dj.iotlite.spec.SpecV1 getSpec(DeviceActionForm action) {
        Optional<Device> d = deviceRepository.findFirstBySnAndProductSn(action.getDeviceSn(), action.getProductSn());
        if (d.isPresent()) {
            SpecV1 specV1 = new SpecV1();
            Gson gson = new Gson();
            try {
                return (SpecV1) specV1.fromJson(gson.toJson(d.get().getSpec()));
                //执行命令
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("model parse exception");
            }
        } else {
            throw new BusinessException("model parse exception");
        }
    }

    @Autowired
    DeviceLogService deviceLogService;

    /**
     * 对设备发送命令
     *
     * @param action
     * @param userInfo
     * @return
     */
    public Object action(DeviceActionForm action, User userInfo) throws Exception {
        Device device = deviceRepository.findFirstBySnAndProductSn(action.getDeviceSn(), action.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("device not found");
        });
        checkUserCanAccessDevice(device,userInfo);
        ProductVersion product = productVersionRepository.findFirstBySnAndVersion(device.getProductSn(), device.getVersion()).orElseThrow(() -> {
            throw new BusinessException("product version not found");
        });

        Adapter adaptor = adapterRepository.findById(product.getAdapterId()).orElseThrow(() -> {
            throw new BusinessException("adapter version not found");
        });

        HashMap<String, Object> propertys = new HashMap<>();

        var member = String.format(RedisKey.DEVICE, action.getProductSn(), action.getDeviceSn());
        redisCommands.hset(member, action.getName() + ":last_at", String.valueOf(System.currentTimeMillis()));
        //最后一次命令的跟踪id
        String ackId = UUID.getUUID();
        propertys.put("ackId", ackId);
        //设置最后跟踪id
        redisCommands.hset(member, action.getName() + ":last_id", ackId);
        //最后一次命令的响应状态 已经创建
        redisCommands.hset(member, action.getName() + ":last_status", String.valueOf(ActionStatus.values()[0]));


        //透传下发
        if (device.getProxyId() != null) {
            var proxy = deviceRepository.findById(device.getProxyId());
            propertys.put("topic", String.format(RedisKey.DeviceProperty, "default", proxy.get().getProductSn(), proxy.get().getSn()));
            propertys.put("subtopic", "/" + action.getProductSn() + "/" + action.getDeviceSn());
        } else {

        }
        // TODO 处理影子问题
        deviceLogService.Log(device.getSn(), device.getProductSn(), DirectionEnum.Down, "admin", (String) propertys.get("topic"), action.getName(), JsonUtils.toJson(propertys), LogLevel.TRACE);
        if (SideTypeEnum.Server.equals(action.getSide())) {
            //服务端来处理逻辑
            var sp = getSpec(action);

            sp.action(action.getName());
            //找到执行后的 property 下发给设备端
            sp.getProperty().forEach(p -> {
                if (!p.getValue().equals(p.getExpect())) {
                    propertys.put(p.getName(), p.getExpect());
                }
            });
            //通过 ackid追踪 消息任务 进程
            deviceInstance.setPropertys(product, device, propertys, action.getName(), ackId, adaptor);
        } else {
            deviceInstance.setControl(product, device, propertys, action.getName(), ackId, adaptor);
        }

        //数据下发成功
        return ackId;
    }

    public void getProduct(String sn, ProductDto productDto) {
        Product product = productRepository.findFirstBySn(sn).orElse(new Product());
        BeanUtils.copyProperties(product, productDto);
    }

    /**
     * 获取我能访问的设备
     * @param userInfo
     * @return
     */
    public List<OptionDto> getAllProduct(User userInfo) {
        return productRepository.findAllByOwner(userInfo.getId()).stream().map(p -> {
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

    /**
     * TODO 设备日志修改
     * @param deviceQueryForm
     * @param userInfo
     * @return
     */
    public Page<DeviceLog> getDeviceLogList(DeviceLogQueryForm deviceQueryForm, User userInfo) {
        Specification<DeviceLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
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

    public Object location(DeviceLocationForm action, User userInfo) {
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

    public Object saveModel(ProductSaveModelForm form, User userInfo) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        checkUserCanAccessProduct(product,userInfo);
        Gson gson = new Gson();
        product.setSpec(gson.fromJson(form.getSpec(), Object.class));
        productRepository.save(product);
        return true;
    }

    public Object refreshProductKey(ProductRefreshProductKeyForm form, User userInfo) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        checkUserCanAccessProduct(product,userInfo);
        product.setSecKey(UUID.getUUID());
        productRepository.save(product);
        return product.getSecKey();
    }

    public Object refreshDeviceKey(DeviceRefreshDeviceKeyForm form, User userInfo) {

        var device = deviceRepository.findFirstBySnAndProductSn(form.getDeviceSn(), form.getProductSn()).orElseThrow(
                () -> {
                    throw new BusinessException("设备不存在或者已经被删除");
                }
        );
        checkUserCanAccessDevice(device,userInfo);
        device.setDeviceKey(UUID.getUUID());
        deviceRepository.save(device);
        return device.getDeviceKey();
    }

    public Object changeTags(ChangeTagsForm form, User userInfo) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        checkUserCanAccessProduct(product,userInfo);
        product.setTags(form.getTags());
        productRepository.save(product);
        return true;
    }

    public Object deviceChangeTags(DeviceChangeTagsForm form, User userInfo) {
        var product = productRepository.findFirstBySn(form.getProductSn()).orElseThrow(() -> {
            throw new BusinessException("产品不存在或者已经被删除");
        });
        var device = deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).orElseThrow(
                () -> {
                    throw new BusinessException("设备不存在或者已经被删除");
                }
        );
        checkUserCanAccessDevice(device,userInfo);
        device.setTags((form.getTags()));
        deviceRepository.save(device);
        return true;
    }

    public Object deviceGroupSave(DeviceGroupSaveForm deviceDto, User userInfo) {
        var deviceGroup = new DeviceGroup();
        deviceGroupRepository.findFirstByName(deviceDto.getName()).ifPresentOrElse((e) -> {
            throw new BusinessException("设备分组已经存在");
        }, () -> {
            BeanUtils.copyProperties(deviceDto, deviceGroup);
            deviceGroup.setOwner(userInfo.getId());
            deviceGroup.setCreatedAt(System.currentTimeMillis());
            deviceGroupRepository.save(deviceGroup);
        });
        return deviceGroup;
    }

    public Page<DeviceGroup> getDeviceGroupList(DeviceQueryForm deviceQueryForm, User userInfo) {
        Specification<DeviceGroup> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(deviceQueryForm.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + deviceQueryForm.getWords() + "%"),
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + deviceQueryForm.getWords() + "%")
                ));
            }
            list.add(criteriaBuilder.equal(root.get("owner").as(Long.class), userInfo.getId()));
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return deviceGroupRepository.findAll(specification, deviceQueryForm.getPage());
    }

    public DeviceGroupDto queryDeviceGroup(Long id, User userInfo) {
        var deviceGroupDto = new DeviceGroupDto();
        DeviceGroup group = deviceGroupRepository.findById(id).orElse(new DeviceGroup());

        checkUserCanAccessGroup(group,userInfo);
        BeanUtils.copyProperties(group, deviceGroupDto);
        Script s = GroupInstanceImpl.groupScriptMapping.get(group.getName());
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
     * @param userInfo
     * @return
     */
    public com.dj.iotlite.api.dto.Page<DeviceListDto> getGroupDeviceList(DeviceQueryForm deviceQueryForm, User userInfo) {
        Gson gson = new Gson();

        var ret = new com.dj.iotlite.api.dto.Page<DeviceListDto>();
        List<DeviceListDto> list = new ArrayList<>();
        PageHelper.startPage(1, 10);

        var data = deviceMapper.getGroupDeviceList(deviceQueryForm);

        data.stream().forEach(d -> {
            var t = new DeviceListDto();
            BeanUtils.copyProperties(d, t);
            var pto = new ProductDto();
            var p = productVersionRepository.findFirstBySnAndVersion(d.getProductSn(), d.getVersion()).orElse(new ProductVersion());
            BeanUtils.copyProperties(p, pto);
            t.setProduct(pto);
            t.setTags(gson.fromJson(String.valueOf(d.getTags()), List.class));
            list.add(t);
        });
        ret.setList(list);
        ret.setTotal(data.getTotal());
        return ret;
    }

    public Object queryDeviceGroup(String name, User user) {
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

    public Object saveGroupFence(DeviceGroupFenceSaveForm form, User userInfo) {
        deviceGroupRepository.findById(form.getId()).ifPresent(d -> {
            d.setFence(form.getFence());
            deviceGroupRepository.save(d);
        });
        return true;
    }

    public Object saveGroupPlayground(DeviceGroupSpecSaveForm form, User userInfo) {
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
            var key = String.format(RedisKey.DEVICE_GROUP, d.getName());
            redisCommands.hset(key, "playground", form.getSpec());
        });
        return true;
    }

    public Object setDeviceMeta(DeviceMetaForm form, User userInfo) {
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
     * @param userInfo
     * @return
     */
    public Object getMapDeviceList(MapDeviceQueryForm query, User userInfo) {
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

    public Object groupStateClean(DeviceGroupCleanForm deviceGroupCleanForm, User userInfo) {
        deviceGroupRepository.findById(deviceGroupCleanForm.getId()).ifPresent(g -> {
            var s = GroupInstanceImpl.groupScriptMapping.get(g.getName());
            if (s != null) {
                ((StateAble) s.getProperty("state")).cleanAll();
            }
        });
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object groupRemove(DeviceGroupRemoveForm groupRemoveForm, User userInfo) {
        deviceGroupRepository.deleteById(groupRemoveForm.getId());
        deviceGroupLinkRepository.deleteInBatch(deviceGroupLinkRepository.findAllByGroupId(groupRemoveForm.getId()));
        return true;
    }

    @Autowired
    UserRepository userRepository;

    public void deviceRegister(RegisterDto regDto) {
        var form = new DeviceSaveForm();
        form.setVersion(regDto.getVersion());
        form.setProductSn(regDto.getProductSn());
        form.setSn(regDto.getDeviceSn());
        form.setName(regDto.getDeviceSn());
        form.setHdVersion(regDto.getHdVersion());
        form.setRegType(RegTypeEnum.device_auto);

        User user=new User();
        var product=productRepository.findFirstBySn(regDto.getProductSn());
        if(product.isPresent()){
            user=userRepository.findById(product.get().getOwner()).get();
            if (product.get().getDiscover().equals(ProductDiscoverEnum.all) || product.get().getDiscover().equals(ProductDiscoverEnum.auto)) {
                return;
            }
        }

        saveDevice(form, user);
    }

    public Object saveAccess(ProductSaveAccessForm form, User userInfo) {
        productRepository.findFirstBySn(form.getSn()).ifPresentOrElse(p -> {
            checkUserCanAccessProduct(p,userInfo);
            p.setAccess(form.getAccess());
            productRepository.save(p);
        }, () -> {
            throw new BusinessException("not found product");
        });
        return true;
    }

    public Object saveDeviceAccess(DeviceSaveAccessForm form, User userInfo) {
        deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).ifPresentOrElse(d -> {
            checkUserCanAccessDevice(d,userInfo);
            d.setAccess(form.getAccess());
            deviceRepository.save(d);
        }, () -> {
            throw new BusinessException("not found device");
        });
        return true;
    }

    public Object saveProductBase(ProductSaveBaseForm form, User userInfo) {
        productRepository.findFirstBySn(form.getSn()).ifPresentOrElse(p -> {
            checkUserCanAccessProduct(p,userInfo);
            p.setName(form.getName());
            p.setDescription(form.getDescription());
            productRepository.save(p);
        }, () -> {
            throw new BusinessException("not found product");
        });
        return true;
    }

    public Object saveBase(DeviceSaveBaseForm form, User userInfo) {
        deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).ifPresentOrElse(d -> {
            checkUserCanAccessDevice(d,userInfo);
            d.setName(form.getName());
            d.setDescription(form.getDescription());
            deviceRepository.save(d);
        }, () -> {
            throw new BusinessException("not found device");
        });
        return true;
    }

    public Object saveGroupBase(DeviceSaveGroupBaseForm form, User userInfo) {
        deviceGroupRepository.findById(form.getId()).ifPresentOrElse(d -> {
            checkUserCanAccessGroup(d,userInfo);
            d.setName(form.getName());
            d.setDescription(form.getDescription());
            deviceGroupRepository.save(d);
        }, () -> {
            throw new BusinessException("group not found");
        });
        return true;
    }

    public Object saveDeviceModel(DeviceSaveModelForm form, User userInfo) {
        deviceRepository.findFirstBySnAndProductSn(form.getSn(), form.getProductSn()).ifPresent(d -> {
            checkUserCanAccessDevice(d,userInfo);
            if (ReleaseTypeEnum.Alpha.equals(d.getReleaseType())) {
                Gson gson = new Gson();
                d.setSpec(gson.fromJson(form.getSpec(), Object.class));
                deviceRepository.save(d);
            }
        });
        return true;
    }
}
