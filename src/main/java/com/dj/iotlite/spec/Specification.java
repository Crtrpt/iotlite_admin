package com.dj.iotlite.spec;

public interface Specification {
    public Specification fromJson(String json) throws Exception;

    public void action(String name) throws Exception;
}
