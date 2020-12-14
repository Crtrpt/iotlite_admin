package com.dj.iotlite.spec;


import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class SpecV1Test {

    @Test
    void fromJson() throws Exception {
        File f = new File("./spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String specV1Json;
        String sp = "";

        while ((specV1Json = br.readLine()) != null) {
            sp = sp + specV1Json;
        }

        SpecV1 specV1 = new SpecV1();

        specV1.fromJson(sp);

        System.out.println(specV1.getProperty("power"));

        System.out.println(specV1.getProperty("temperature"));


        specV1.action("turn on");

        System.out.println(specV1.getProperty("power"));

        specV1.action("turn off");

        System.out.println(specV1.getProperty("power"));

        specV1.action("turn on");

        System.out.println(specV1.getProperty("power"));

        specV1.action("turn up");

        System.out.println(specV1.getProperty("temperature"));

        specV1.action("turn down");

        System.out.println(specV1.getProperty("temperature"));

        specV1.action("reset");

        System.out.println(specV1.getProperty("power"));

        System.out.println(specV1.getProperty("temperature"));

    }
}