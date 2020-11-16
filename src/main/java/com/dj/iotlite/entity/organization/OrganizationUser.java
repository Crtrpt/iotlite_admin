package com.dj.iotlite.entity.organization;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Data
@SQLDelete(sql = "update `organization_user` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "organization_user")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class OrganizationUser extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String organizationUuid;

    String userUuid;
}
