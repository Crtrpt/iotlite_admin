package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.OptionDto;
import com.dj.iotlite.api.dto.ProductDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.GetAllVersionForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
import com.dj.iotlite.api.form.VersionRemoveForm;
import com.dj.iotlite.entity.repo.DeviceGroupLinkRepository;
import com.dj.iotlite.entity.repo.DeviceRepository;
import com.dj.iotlite.entity.repo.ProductRepository;
import com.dj.iotlite.entity.product.ProductVersion;
import com.dj.iotlite.entity.repo.ProductVersionRepository;
import com.dj.iotlite.exception.BusinessException;
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

@Service
@Slf4j
public class VersionServiceImpl implements VersionService {

    @Autowired
    ProductVersionRepository productVersionRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Boolean newVersion(NewVersionReleaseForm form) {
        log.info("创建新版本");
        productVersionRepository.findFirstBySnAndVersion(form.getProductSn(), form.getVersion()).ifPresent(p -> {
            throw new BusinessException("版本号不能重复");
        });

        var newProduct = new ProductVersion();
        productRepository.findFirstBySn(form.getProductSn()).ifPresentOrElse((p) -> {
            BeanUtils.copyProperties(p, newProduct);
            newProduct.setVerDescription(form.getVerDescription());
            newProduct.setVersion(form.getVersion());
            newProduct.setMinHdVersion(form.getMinHdVersion());
            newProduct.setCreatedAt(System.currentTimeMillis());
            newProduct.setStartAt(form.getStartAt());
            newProduct.setEndAt(form.getEndAt());
            newProduct.setDeviceCount(0L);
            productVersionRepository.save(newProduct);
        }, () -> {
            throw new BusinessException("产品不存在");
        });
        return true;
    }

    /**
     * 获取产品的更新版本
     *
     * @param query
     * @return
     */
    @Override
    public Page<ProductVersion> getVersionList(DeviceQueryForm query) {
        Specification<ProductVersion> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        criteriaBuilder.like(root.get("sn").as(String.class), "%" + query.getWords() + "%"),
                        criteriaBuilder.like(root.get("ver").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%"),
                        criteriaBuilder.like(root.get("tags").as(String.class), query.getWords() + "%")
                ));
            }
            list.add(criteriaBuilder.equal(root.get("sn").as(String.class), query.getProductSn()));

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return productVersionRepository.findAll(specification, query.getPage());
    }

    @Override
    public List<OptionDto> getAll(GetAllVersionForm query) {
        List<OptionDto> res = new ArrayList<>();
        var p = productRepository.findById(query.getId()).orElseThrow(() -> {
            throw new BusinessException("not found product");
        });
        OptionDto optionDto = new OptionDto();
        optionDto.setId(-1L);
        optionDto.setLabel("开发版");
        res.add(optionDto);
        productVersionRepository.findAllBySn(p.getSn()).stream().forEach(p1 -> {
            OptionDto opt = new OptionDto();
            opt.setId(p1.getId());
            opt.setLabel(p1.getVersion());
            res.add(opt);
        });
        return res;
    }

    @Override
    public Object queryProductVersion(String sn, String version) {
        var ret = new ProductDto();
        var p = productVersionRepository.findFirstBySnAndVersion(sn, version).orElseThrow(() -> {
            throw new BusinessException("not found product version");
        });
        BeanUtils.copyProperties(p, ret);
        return ret;
    }

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceGroupLinkRepository deviceGroupLinkRepository;

    @Override
    public Object removeProductVersion(VersionRemoveForm form) {
        productVersionRepository.findById(form.getId()).ifPresentOrElse((p) -> {
            //删除所有设备
            deviceRepository.deleteAll(deviceRepository.findAllByProductSnAndVersion(p.getSn(),p.getVersion()));
            //删除组内关系
            var links = deviceGroupLinkRepository.findAllByProductSnAndVersion(p.getSn(),p.getVersion());
            deviceGroupLinkRepository.deleteInBatch(links);
            //删除版本
            productVersionRepository.delete(p);
            // TODO 清理各种状态
        }, () -> {
            throw new BusinessException("version not found");
        });
        return true;
    }
}
