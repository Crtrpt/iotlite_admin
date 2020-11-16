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
    String Uuid;

    String name;

    String remark;

    String account;

    String password;

}
