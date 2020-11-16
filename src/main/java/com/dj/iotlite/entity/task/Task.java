package com.dj.iotlite.entity.task;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `task` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "task")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class Task extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 任务内容
     */
    String content;

    String Uuid;
}
