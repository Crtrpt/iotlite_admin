package com.dj.iotlite.spec;


import com.dj.iotlite.spec.exception.NotFoundActionException;
import com.dj.iotlite.spec.exception.NotFoundPropertyException;
import com.google.gson.Gson;
import groovy.lang.GroovyShell;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class SpecV1 implements Specification {

    @Override
    public  Specification fromJson(String json) throws Exception {
        Gson gson=new Gson();
        SpecV1 specV1 = gson.fromJson(json,SpecV1.class);
        BeanUtils.copyProperties(specV1,this);
        return this;
    }

    @Override
    public void setProperty(String property, Integer value) throws Exception {
        this.property.forEach(p->{
            if(property.equals(p.name)){
                p.expect=value;
            }
        });
    }

    @Override
    public void action(String name) throws Exception {
        if (name.equals("reset")) {
            this.property.forEach(p -> {
                p.setExpect(p.defaultValue);
            });
            return;
        }
        for (Control c : this.control) {
            if (c.name.equals(name)) {
                //执行控制脚本
                GroovyShell gs = new GroovyShell();
                this.property.forEach(p -> {
                    gs.setVariable(p.name, p.expect);
                });
                System.out.println("EXEC -> "+c.action);
                gs.evaluate(c.action);
                this.property.forEach(p -> {
                    p.setExpect((int) gs.getVariable(p.name));
                });
                return;
            }
        }
        throw new NotFoundActionException(name);
    }

    @Data
    static public class  Property {
        String name;
        String desc;
        Integer value;

        Integer defaultValue;
//        String type;
        Integer threshold;

        Integer expect;

        public void setExpect(Integer expect) {
            this.expect = expect;
        }
    }

    @Data
    static class  Alarm {
        String name;
        String desc;
        Integer level;
        Integer interval;
        String condition;
        String resume;
    }

    @Data
    static class  Control {
        String name;
        String desc;
        String action;
    }

    @Data
    static class  Event {
        String name;
        String desc;
        String condition;
        String resume;
    }

    @Data
    static class  Metric {
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

    public Integer getProperty(String property) {
        for (Property property1 : this.property) {
            if(property1.getName().equals( property)){
                return  property1.getExpect();
            }
        }
        throw new NotFoundPropertyException();
    }

    List<Alarm> alarm;
    List<Event> event;
    List<Control> control;
    List<Metric> metric;
}


