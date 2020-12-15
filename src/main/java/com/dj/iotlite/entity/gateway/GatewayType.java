package com.dj.iotlite.entity.gateway;

import com.dj.iotlite.entity.Base;
import com.dj.iotlite.enums.GatewayEnum;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashMap;

@Data
@SQLDelete(sql = "update `gateway_type` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "gateway_type")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class GatewayType extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * http网关 websocket网关 mqtt网关
     */
    String name;

    String description;

    String plugin;
}
