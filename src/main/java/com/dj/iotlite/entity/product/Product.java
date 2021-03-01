package com.dj.iotlite.entity.product;

import com.dj.iotlite.entity.Base;
import com.dj.iotlite.enums.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `product` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "product")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String sn;
    
    String name;

    String description;

    /**
     * 产品图标
     */
    String icon;
    /**
     * 产品uuid
     */
    String uuid;

    /**
     * 网关类型
     */
    Integer type;


    /**
     * 产品标签
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Object tags;


    /**
     * 产品物模型
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Object spec;


    @Column(columnDefinition = "int default 0")
    SpecFileEnum specFileType;

    String secKey;

    /**
     * 每次修改版本加一
     */
    String ver;

    /**
     * 默认后台手动添加
     */
    @Column(columnDefinition = "int default 0")
    ProductDiscoverEnum discover;
    /**
     * 设备认证方式默认不需要认证
     */
    @Column(columnDefinition = "int default 0")
    DeviceCertEnum deviceCert;

    /**
     * 默认终端设备
     */
    @Column(columnDefinition = "int default 0")
    WorkTypeEnum workType;

    /**
     * 默认mqtt
     */
    @Column(columnDefinition = "int default 0")
    ProtocolTypeEnum protocolType;

    Long adaptorId;
}
