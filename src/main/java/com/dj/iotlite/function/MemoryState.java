package com.dj.iotlite.function;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryState implements StateAble {
    HashMap<String, AtomState> states = new HashMap<>();

    public void set(String key, Object value) {
        states.put(key, new AtomState(value, -1L));
    }

    public AtomState get(String key, Object defaultValue) {
        states.putIfAbsent(key, new AtomState(defaultValue, -1L));
        return states.get(key);
    }

    public AtomState get(String key) {
        states.putIfAbsent(key, new AtomState(0, -1L));
        return states.get(key);
    }

    public void increase(String key, Integer defaultValue) {
        var v = get(key, new AtomicLong(defaultValue));
        ((AtomicLong) v.value).incrementAndGet();
        v.time=System.currentTimeMillis();
    }

    public void decrease(String key, Integer defaultValue) {
        var v = get(key, new AtomicLong(defaultValue));
        ((AtomicLong) v.value).decrementAndGet();
        v.time=System.currentTimeMillis();
    }

    @Override
    public Object getStates() {
        return states;
    }

    @Override
    public void cleanAll() {
        states=new HashMap<>();
    }

}
