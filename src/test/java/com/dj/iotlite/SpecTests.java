package com.dj.iotlite;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class SpecTests {
    @Test
    void  gen(){
        String spec="{\n" +
                "  \"version\": \"01\",\n" +
                "  \"name\": \"example_light\",\n" +
                "  \"property\": {\n" +
                "    \"power\": \"20\"\n" +
                "  },\n" +
                "  \"event\": {\n" +
                "    \"off\": \"power==0\",\n" +
                "    \"on\": \"power>0\"\n" +
                "  },\n" +
                "  \"control\": {\n" +
                "    \"on\": \"power=100\"\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
    }
}
