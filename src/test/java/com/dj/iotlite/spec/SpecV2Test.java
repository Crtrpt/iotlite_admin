package com.dj.iotlite.spec;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpecV2Test {

    @Test
    void fromJson() throws Exception {

        File f = new File("./spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String specV1Json;
        String sp = "";

        while ((specV1Json = br.readLine()) != null) {
            sp = sp + specV1Json;
        }


        var startAt = System.currentTimeMillis();
        SpecV2 specV2 = new SpecV2();

        specV2.fromJson(sp);
        for (int i = 0; i < 100_000_00; i++) {
            specV2.action("turn up");
            specV2.action("turn down");
        }

        System.out.println("温度值"+specV2.getProperty("temperature").getValue());
        var endAt = System.currentTimeMillis();
        var v2gap = endAt - startAt;

        startAt = System.currentTimeMillis();
        SpecV1 specV1 = new SpecV1();
        specV1.fromJson(sp);
        for (int i = 0; i < 100_000_00; i++) {
            specV1.action("turn up");
            specV1.action("turn down");
        }
        System.out.println("温度值"+specV1.getProperty("temperature"));
        endAt = System.currentTimeMillis();

        var v1gap = endAt - startAt;

        System.out.println("V2 :" + v2gap);
        System.out.println("V1 :" + v1gap);
    }
}