package com.dj.iotlite.entity.device;

import com.dj.iotlite.entity.Base;
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

    /**
     * 设备的uuid
     */
    String uuid;


    Long productId;
    /**
     * 设备影子
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    HashMap shadow;

    /**
     * 每次修改版本加一
     */
    Integer version;

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
     * 产品版本
     */
    String ver;
}
