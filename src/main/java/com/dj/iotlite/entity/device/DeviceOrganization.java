package com.dj.iotlite.entity.device;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "device_organization")
@DynamicUpdate
@Cacheable
public class DeviceOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String deviceUuid;

    String orgazaitionUuid;
}
