package com.dj.iotlite.event;

import com.dj.iotlite.entity.product.ProductVersion;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductVersionEvent extends ApplicationEvent {

    ProductVersion product;

    public ProductVersionEvent(Object source, ProductVersion productVersion) {
        super(source);
    }
}