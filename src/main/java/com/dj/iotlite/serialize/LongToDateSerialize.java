package com.dj.iotlite.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LongToDateSerialize extends JsonSerializer<Long> {


    Calendar calendar = Calendar.getInstance();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            gen.writeString("-");
        } else {
            calendar.setTimeInMillis(value);
            gen.writeString(simpleDateFormat.format(calendar.getTime()));
        }
    }
}
