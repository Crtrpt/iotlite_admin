package com.dj.iotlite.event;

import com.dj.iotlite.entity.product.Product;
import com.dj.iotlite.entity.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangeProduct extends ApplicationEvent {
    Action action;
    Product product;

    public ChangeProduct(Object source, Product product, Action action) {
        super(source);
        this.product = product;
        this.action = action;
    }

    public enum Action {
        ADD,
        REMOVE,
        CREATE,
    }
}