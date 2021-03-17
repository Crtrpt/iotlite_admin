package com.dj.iotlite.function;

public class AtomState {
    Object value;
    Long ttl;

    public AtomState(Object value, Long ttl) {
        this.value = value;
        this.ttl = ttl;
    }
}
