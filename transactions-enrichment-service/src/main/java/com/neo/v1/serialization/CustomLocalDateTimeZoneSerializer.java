package com.neo.v1.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.neo.core.exception.InvalidDateException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor
public class CustomLocalDateTimeZoneSerializer extends JsonSerializer<ZonedDateTime> {

    public void serialize(ZonedDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        String fieldName = jsonGenerator.getOutputContext().getCurrentName();

        try {
            jsonGenerator.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")));
        } catch (IOException | DateTimeParseException var6) {
            throw new InvalidDateException(fieldName, var6);
        }
    }
}
