package com.dj.iotlite.entity.device;

import com.dj.iotlite.entity.Base;
import com.dj.iotlite.enums.DirectionEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `device_log` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "device_log")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class DeviceLog extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String source;

    String target;
    /**
     *  data  direction
     */
    DirectionEnum direction= DirectionEnum.UP;
    /**
     * description
     */
    String description;
    /**
     * raw data
     */
    String rawData;
}
