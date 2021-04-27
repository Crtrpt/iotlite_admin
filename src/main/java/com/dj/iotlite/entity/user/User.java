package com.dj.iotlite.entity.user;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `user` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "user")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * user uuid
     */
    String uuid;

    String name;

    String description;

    String account;

    String password;

    /**
     * 用户所属组织机构
     */
    Long organizationId;

    String email;

    String phone;

    Boolean isActive;

    String avatar;
}
