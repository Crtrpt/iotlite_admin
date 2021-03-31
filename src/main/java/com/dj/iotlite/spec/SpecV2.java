package com.dj.iotlite.spec;

import com.dj.iotlite.spec.exception.NotFoundActionException;
import com.google.gson.Gson;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecV2 implements Specification {

    @Override
    public Specification fromJson(String json) throws Exception {
        Gson gson = new Gson();
        HashMap instance = gson.fromJson(json, HashMap.class);
        List property = (List) instance.get("property");
        property.forEach(s -> {
            var s1 = (Map<String, Object>) s;
            this.propertys.put((String) s1.get("name"), new Property(s1));
        });


        List control = (List) instance.get("control");
        control.forEach(s -> {
            var s1 = (Map<String, Object>) s;
            this.control.put((String) s1.get("name"), new GroovyShell().parse((String) s1.get("action")));
        });
        return this;
    }

    @Override
    public void action(String name) throws Exception {
        var script = this.control.get(name);
        this.propertys.forEach((key, s1) -> {
            script.setProperty(key, s1.getValue());
        });
        script.run();
        this.propertys.forEach((key, s1) -> {
            this.getProperty(key).setValue(script.getProperty(key));
        });
    }

    @Data
    static public class Property {
        String name;
        String desc;
        Object value;
        Integer threshold;
        Integer expect;

        Property(Map<String, Object> source) {
            this.name = (String) source.get("name");
            this.value = source.get("value");
        }
    }

    @Data
    static class Control {
        String name;
        String desc;
        String action;
    }


    HashMap<String, Property> propertys = new HashMap<>();

    public Property getProperty(String property) {
        return propertys.get(property);
    }

    Map<String, Script> control = new HashMap<>();
}
