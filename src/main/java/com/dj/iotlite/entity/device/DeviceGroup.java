package com.dj.iotlite.entity.device;

import com.dj.iotlite.entity.Base;
import com.dj.iotlite.enums.AccessTypeEnum;
import com.dj.iotlite.enums.OwnerTypeEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `device_group` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "device_group")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class DeviceGroup extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 设备分组名称
     */
    String name;

    /**
     * 备注
     */
    String description;
    /**
     * 设备数量
     */
    Long deviceCount;
    /**
     * 分组围栏
     */
    @Column(columnDefinition = "json")
    String fence;
    /**
     * 分支的规则引擎 groovy 实现用户自定义的逻辑
     */
    String spec;

    /**
     * 默认所有人可见
     */
    @Column(columnDefinition = "int default 0")
    AccessTypeEnum access;

    /**
     * 属主类型
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
}
