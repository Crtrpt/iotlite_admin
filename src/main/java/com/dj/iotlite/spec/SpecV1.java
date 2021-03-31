package com.dj.iotlite.spec;


import com.dj.iotlite.spec.exception.NotFoundActionException;
import com.dj.iotlite.spec.exception.NotFoundPropertyException;
import com.google.gson.Gson;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;

@Data
public class SpecV1 implements Specification {

    //json 实例化到 SpecV1
    @Override
    public Specification fromJson(String json) throws Exception {
        Gson gson = new Gson();
        SpecV1 specV1 = gson.fromJson(json, SpecV1.class);
        BeanUtils.copyProperties(specV1, this);
        this.control.forEach(c -> {
            c.script = new GroovyShell().parse((String) c.action);
        });
        return this;
    }

    public void setProperty(String property, Integer value) throws Exception {
        this.property.forEach(p -> {
            if (property.equals(p.name)) {
                p.expect = value;
            }
        });
    }

    @Override
    public void action(String name) throws Exception {
        for (Control c : this.control) {
            if (c.name.equals(name)) {
                //执行控制脚本
                this.property.forEach(p -> {
                    c.script.setProperty(p.name, p.expect);
                });
                c.script.run();
                this.property.forEach(p -> {
                    p.setExpect(c.script.getProperty(p.name));
                });
                return;
            }
        }
        throw new NotFoundActionException(name);
    }

    @Data
    static public class Property {
        String name;
        String desc;
        Object value;

        Object expect;
        //        String type;
        Integer threshold;

        public void setExpect(Object expect) {
            this.expect = expect;
        }
    }

    @Data
    static class Alarm {
        String name;
        String desc;
        Integer level;
        Integer interval;
        String condition;
        String resume;
    }

    @Data
    static class Control {
        String name;
        String desc;
        String action;
        Script script;
    }

    @Data
    static class Event {
        String name;
        String desc;
        String condition;
        String resume;
    }

    @Data
    static class Metric {
        @Data
        static class Source {
            String type;
            String name;
        }

        String name;
        Source source;
    }

    String name;

    String desc;

    Integer version;

    List<Property> property;

    public Object getProperty(String property) {
        for (Property property1 : this.property) {
            if (property1.getName().equals(property)) {
                return property1.getExpect();
            }
        }
        throw new NotFoundPropertyException();
    }

    List<Alarm> alarm;
    List<Event> event;

    List<Control> control;
    List<Metric> metric;
}


