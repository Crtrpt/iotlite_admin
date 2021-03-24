package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.OptionDto;
import com.dj.iotlite.api.form.DeviceQueryForm;
import com.dj.iotlite.api.form.GetAllVersionForm;
import com.dj.iotlite.api.form.NewVersionReleaseForm;
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
        List<OptionDto> res=new ArrayList<>();
        var  p= productRepository.findById(query.getId()).orElseThrow(()->{
            throw new BusinessException("not found product");
        });
        productVersionRepository.findAllBySn(p.getSn()).stream().forEach(p->{
            OptionDto optionDto=new OptionDto();
            optionDto.setLabel(p.getVersion());
            res.add(optionDto);
        });
        return res;
    }
}
