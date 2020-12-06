package com.dj.iotlite.entity.product;

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
@SQLDelete(sql = "update `gateway` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "gateway")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class Gateway extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    GatewayEnum edgetype;

    String uuid;

    /**
     * 服务器端网关
     */
    String ServiceGatewayUuid;

    /**
     * 边缘网关UUID
     */
    String EdgeGatewayUuid;

    /**
     * 产品物模型
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    HashMap setting;
}
