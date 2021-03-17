package com.dj.iotlite.function;

import java.io.Serializable;


public class AtomState implements Serializable {
    public Object value;
    public Long ttl;
    public Long time;

    public void setValue(Object value) {
        this.value = value;
        this.time = System.currentTimeMillis();
    }

    public AtomState(Object value, Long ttl) {
        this.value = value;
        this.ttl = ttl;
        this.time = System.currentTimeMillis();
    }
}
