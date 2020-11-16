package com.dj.iotlite.entity.task;

import com.dj.iotlite.entity.Base;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `task_handler` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "task_handler")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
public class TaskHandler extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 任务处理日志
     */
    String user_uuid;


    String task_uuid;
    /**
     * 处理日志
     */
    String content;
}
