package com.dj.iotlite.entity.setting;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `setting` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "setting")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Setting extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    /**
     * setting value
     */
    String val;

}
