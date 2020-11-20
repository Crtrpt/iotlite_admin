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
    String remark;

    /**
     * 设备的uuid
     */
    String uuid;

    /**
     * 关联的产品的uuid
     */
    String productUuid;
    /**
     * 设备影子
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    HashMap shadow;
}
