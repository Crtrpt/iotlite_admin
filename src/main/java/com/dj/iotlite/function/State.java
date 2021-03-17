package com.dj.iotlite.function;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class State {
    HashMap<String, AtomState> states = new HashMap<>();

    void set(String key, Object value) {
        states.put(key, new AtomState(value, -1L));
    }

    AtomState get(String key, Object defaultValue) {
        states.putIfAbsent(key, new AtomState(defaultValue, -1L));
        return states.get(key);
    }

    AtomState get(String key) {
        states.putIfAbsent(key, new AtomState(0, -1L));
        return states.get(key);
    }

    void increase(String key, Integer defaultValue) {
        var v = get(key, new AtomicLong(defaultValue));
        ((AtomicLong) v.value).incrementAndGet();
    }

    void decrease(String key, Integer defaultValue) {
        var v = get(key, new AtomicLong(defaultValue));
        ((AtomicLong) v.value).decrementAndGet();
    }

}
