package com.dj.iotlite.entity.product;

import com.dj.iotlite.enums.*;
import com.dj.iotlite.listener.DeviceListener;
import com.dj.iotlite.listener.ProductListener;
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
@EntityListeners(ProductEntityListener.class)
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product extends ProductBase {

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

    /**
     * 格式类型
     * 默认JSON
     */
    @Column(columnDefinition = "int default 0")
    SpecFileEnum specFileType;


    String secKey;

    /**
     * 产品版本
     */
    String version;

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

    /**
     * 设备适配的协议
     */
    @Column(columnDefinition = "int default 1")
    Long adapterId;

    /**
     * 使用什么解释器执行内嵌脚本 默认groovy执行
     */
    @Column(columnDefinition = "int default 0")
    InterpreterTypeEnum interpreter;

    /**
     * 设备数量
     */
    Long deviceCount;


}
