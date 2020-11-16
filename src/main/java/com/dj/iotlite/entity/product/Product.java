package com.dj.iotlite.entity.product;

import com.dj.iotlite.entity.Base;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@SQLDelete(sql = "update `product` SET deleted_at =  unix_timestamp(now()) WHERE id = ?")
@Entity
@Table(name = "product")
@Where(clause = "deleted_at is null")
@DynamicUpdate
@Cacheable
@org.hibernate.annotations.TypeDef(name = "json", typeClass = JsonStringType.class)
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String remark;

    String uuid;

    /**
     * 产品物模型
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    String spec;
}
