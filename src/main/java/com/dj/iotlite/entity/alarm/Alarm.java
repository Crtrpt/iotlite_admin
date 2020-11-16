package com.dj.iotlite.entity.alarm;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `alarm` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "alarm")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Alarm extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String deviceUuid;

    String content;

    String Uuid;

}
