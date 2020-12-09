package com.dj.iotlite;

import com.dj.iotlite.spec.Specification;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SpecTests {

    @Test
    void  gen() throws IOException {
        File f=new File("../spec/temperature.spec.json");

        BufferedReader br = new BufferedReader(new FileReader(f));

        String specV1;

        while ((specV1 = br.readLine()) != null)
            System.out.println(specV1);
        }

        Gson gson=new Gson();

}
