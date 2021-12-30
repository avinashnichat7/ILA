package com.neo.v1.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.neo.core.exception.InvalidDateException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@NoArgsConstructor
public class CustomLocalDateTimeZoneDeserializer extends JsonDeserializer<ZonedDateTime> {

    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext context) {
        String fieldName = jsonParser.getParsingContext().getCurrentName();

        try {
            return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        } catch (IOException | DateTimeParseException var5) {
            throw new InvalidDateException(fieldName, var5);
        }
    }
}
