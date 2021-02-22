package com.dj.iotlite.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StringMaskSerialize extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            gen.writeString("-");
        } else {
            var len = value.length();
            if (value.length() > 4) {
                gen.writeString("****" + value.substring(len - 4, len));
            }else {
                gen.writeString("****");
            }
        }
    }
}
