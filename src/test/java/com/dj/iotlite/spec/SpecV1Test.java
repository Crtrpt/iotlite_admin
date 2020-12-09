package com.dj.iotlite.spec;

import com.google.gson.Gson;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SpecV1Test {

    @Test
    void fromJson() throws Exception {
        File f=new File("./spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String specV1Json;
        String sp="";

        while ((specV1Json = br.readLine()) != null){
            sp=sp+specV1Json;
        }
        //            System.out.println(specV1Json);
        SpecV1 specV1=new SpecV1();
        specV1.fromJson(sp);
        String[] roots = new String[] { "." };

        specV1.action("turn on");

        specV1.getProperty().stream().forEach(p->{
            System.out.println(p.value);
        });

        specV1.action("turn off");

        specV1.getProperty().stream().forEach(p->{
            System.out.println(p.value);
        });


    }
}