package com.dj.iotlite.entity.device;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `device_group_link` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "device_group_link")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class DeviceGroupLink extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 设备分组名称
     */
    String groupName;

    /**
     * 分组id
     */
    Long groupId;
    /**
     * 备注
     */
    String deviceSn;
    /**
     * 产品Sn
     */
    String productSn;

    Long deviceId;


}
