package com.dj.iotlite.function;

public interface StateAble {


    public void set(String key, Object value) ;

    public AtomState get(String key, Object defaultValue);

    public AtomState get(String key);

    public void increase(String key, Integer defaultValue);

    public void decrease(String key, Integer defaultValue);

    Object getStates();

    void cleanAll();
}
