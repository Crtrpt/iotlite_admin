package com.dj.iotlite.entity.device;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device_user")
@DynamicUpdate
@Cacheable
public class DeviceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String deviceUuid;

    String userUuid;
}
