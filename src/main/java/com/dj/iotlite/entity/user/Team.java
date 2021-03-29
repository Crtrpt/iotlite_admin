package com.dj.iotlite.entity.user;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `team` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "team")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Team extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 团队名称
     */
    String name;

    String sn;
    /**
     * 团队简介
     */
    String description;

    Long owner;

    /**
     * 成员数量
     */
    Long memberCount;
}
