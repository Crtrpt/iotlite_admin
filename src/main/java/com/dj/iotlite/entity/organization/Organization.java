package com.dj.iotlite.entity.organization;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@SQLDelete(sql = "update `organization` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "organization")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Organization extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String remark;

    String uuid;

}
