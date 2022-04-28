package com.neo.v1.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.neo.core.exception.InvalidDateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomLocalDateTimeZoneDeserializerTest {

    @InjectMocks
    private CustomLocalDateTimeZoneDeserializer subject;

    @Test
    void whenDesializeIsCalledWithJsonParserAndDeserializationContextExpectToReturnLocalDate() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        JsonStreamContext jsonStreamContext = mock(JsonStreamContext.class);
        DeserializationContext context = mock(DeserializationContext.class);
        String fieldName = "valueDate";
        String dateValue = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

        when(jsonParser.getParsingContext()).thenReturn(jsonStreamContext);
        when(jsonStreamContext.getCurrentName()).thenReturn(fieldName);
        when(jsonParser.getValueAsString()).thenReturn(dateValue);

        ZonedDateTime result = subject.deserialize(jsonParser, context);
        ZonedDateTime expected = ZonedDateTime.parse(dateValue, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        assertThat(result.getMonth()).isEqualTo(expected.getMonth());
        assertThat(result.getYear()).isEqualTo(expected.getYear());
        assertThat(result.getDayOfMonth()).isEqualTo(expected.getDayOfMonth());
        assertThat(result.getMinute()).isEqualTo(expected.getMinute());
        assertThat(result.getHour()).isEqualTo(expected.getHour());
        assertThat(result.getSecond()).isEqualTo(expected.getSecond());

        verify(jsonParser).getParsingContext();
        verify(jsonStreamContext).getCurrentName();
        verify(jsonParser).getValueAsString();
    }

    @Test
    void whenDesializeIsCalledWithJsonParserAndDeserializationContextExpectToThrowDateTimeParseException() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        JsonStreamContext jsonStreamContext = mock(JsonStreamContext.class);
        DeserializationContext context = mock(DeserializationContext.class);
        String fieldName = "valueDate";
        String dateValue = "2019/10/11 10:20:30";

        when(jsonParser.getParsingContext()).thenReturn(jsonStreamContext);
        when(jsonStreamContext.getCurrentName()).thenReturn(fieldName);
        when(jsonParser.getValueAsString()).thenReturn(dateValue);

        InvalidDateException dateException = assertThrows(InvalidDateException.class, () -> subject.deserialize(jsonParser, context));

        assertThat(dateException.getFieldName()).isEqualTo(fieldName);
        assertTrue(dateException.getCause() instanceof DateTimeParseException);

        verify(jsonParser).getParsingContext();
        verify(jsonStreamContext).getCurrentName();
        verify(jsonParser).getValueAsString();
    }

    @Test
    void whenDesializeIsCalledWithJsonParserAndDeserializationContextExpectToThrowIOException() throws IOException {
        JsonParser jsonParser = mock(JsonParser.class);
        JsonStreamContext jsonStreamContext = mock(JsonStreamContext.class);
        DeserializationContext context = mock(DeserializationContext.class);
        String fieldName = "valueDate";

        when(jsonParser.getParsingContext()).thenReturn(jsonStreamContext);
        when(jsonStreamContext.getCurrentName()).thenReturn(fieldName);
        when(jsonParser.getValueAsString()).thenThrow(new IOException());

        InvalidDateException dateException = assertThrows(InvalidDateException.class, () -> subject.deserialize(jsonParser, context));

        assertThat(dateException.getFieldName()).isEqualTo(fieldName);
        assertTrue(dateException.getCause() instanceof IOException);

        verify(jsonParser).getParsingContext();
        verify(jsonStreamContext).getCurrentName();
        verify(jsonParser).getValueAsString();
    }
}