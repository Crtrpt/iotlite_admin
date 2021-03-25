package com.dj.iotlite.entity.user;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `team_member` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "team_member")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class TeamMember extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 组织id
     */
    Long teamId;

    /**
     * 用户id
     */
    Long userId;
}
