package com.dj.iotlite.spec;




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
    public void setProperty(String property, String value) throws Exception {
        this.property.forEach(p->{
            if(property.equals(p.name)){
                p.expect=value;
            }
        });
    }

    @Override
    public void action(String name) throws Exception {
        this.control.forEach(c->{
            if(c.name.equals(name)){
                //执行控制脚本
                GroovyShell gs=new GroovyShell();

                this.property.stream().forEach(p->{
                    gs.setVariable(p.name,p.value);
                });
//                System.out.println("执行"+c.action);
                gs.evaluate(c.action);
                this.property.stream().forEach(p->{
                    p.value=String.valueOf(gs.getVariable(p.name));
                });
            }
        });
    }

    static class  Property {
        String name;
        String desc;
        String value;
        String expect;
    }

    @Data
    static class  Alarm {
        String name;
        String desc;
        Integer level;
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
        static class Source {
            String type;
        }
        String name;
        Source source;
    }

    String name;
    String desc;
    Integer version;
    List<Property> property;
    List<Alarm> alarm;
    List<Event> event;
    List<Control> control;
    List<Metric> metric;
}


