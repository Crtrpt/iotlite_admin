package com.dj.iotlite.entity.device;

import com.dj.iotlite.entity.Base;
import com.dj.iotlite.enums.*;
import com.dj.iotlite.listener.DeviceListener;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashMap;

@Data
@SQLDelete(sql = "update `device` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "device")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
@EntityListeners(DeviceEntityListener.class)
public class Device extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String sn;

    String productSn;
    /**
     * 设备图标
     */
    String icon;

    /**
     * 设备名称
     */
    String name;

    /**
     * 设备备注
     */
    String description;


    Long productId;
    /**
     * 设备影子
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    HashMap shadow;

    /**
     * 软件版本
     */
    String version;
    /**
     * 硬件版本
     */
    String hdVersion;

    /**
     * 产品物模型
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Object spec;

    /**
     * 设备标签
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    Object tags;

    /**
     * 影子版本
     */
    Integer ver;

    /**
     * 设备注册方式
     */
    @Column(columnDefinition = "int default 0")
    ProductDiscoverEnum discover;
    /**
     * 设备认证方式默认不需要认证
     */
    @Column(columnDefinition = "int default 0")
    DeviceCertEnum deviceCert;

    /**
     * 设备key
     */
    String deviceKey;

    /**
     * 设备加入的分组
     */
    Long batchId;

    /**
     * 设备加入的分组
     */
    String deviceGroup;

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
     * 设备围栏
     */
    String fence;

    /**
     * 接入点 设备
     */
    Long proxyId;


    /**
     * 默认所有人可见
     */
    @Column(columnDefinition = "int default 0")
    AccessTypeEnum access;



    @Column(columnDefinition = "int default 0")
    RegTypeEnum regType;

    /**
     * 设备更新策略
     */
    @Column(columnDefinition = "int default 0")
    UpdateStrategyEnum updateStrategy;

    /**
     *
     */
    OwnerTypeEnum ownerType;
    /**
     * 设备所有者
     */
    Long owner;

    /**
     * 产品所属团队
     */
    Long team;

    @Column(columnDefinition = "int default 0")
    ReleaseTypeEnum releaseType;
}
