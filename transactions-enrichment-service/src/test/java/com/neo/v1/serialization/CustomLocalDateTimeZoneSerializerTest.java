package com.neo.v1.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.neo.core.exception.InvalidDateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomLocalDateTimeZoneSerializerTest {

    @InjectMocks
    private CustomLocalDateTimeZoneSerializer subject;

    @Test
    void whenDeserializeIsCalledWithJsonParserAndDeserializationContextExpectToReturnLocalDate() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        JsonStreamContext context = mock(JsonStreamContext.class);
        SerializerProvider provider = mock(SerializerProvider.class);
        String fieldName = "valueDate";

        when(jsonGenerator.getOutputContext()).thenReturn(context);
        when(context.getCurrentName()).thenReturn(fieldName);
        doNothing().when(jsonGenerator).writeString(anyString());

        subject.serialize(ZonedDateTime.now(), jsonGenerator, provider);

        verify(jsonGenerator).getOutputContext();
        verify(context).getCurrentName();
        verify(jsonGenerator).writeString(anyString());
    }

    @Test
    void whenDesializeIsCalledWithJsonParserAndDeserializationContextExpectToThrowIOException() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        JsonStreamContext context = mock(JsonStreamContext.class);
        SerializerProvider provider = mock(SerializerProvider.class);
        String fieldName = "valueDate";
        ZonedDateTime dateTime = ZonedDateTime.now();

        when(jsonGenerator.getOutputContext()).thenReturn(context);
        when(context.getCurrentName()).thenReturn(fieldName);
        doThrow(new IOException()).when(jsonGenerator).writeString(anyString());

        InvalidDateException dateException = assertThrows(InvalidDateException.class, () -> subject.serialize(dateTime, jsonGenerator, provider));

        assertThat(dateException.getFieldName()).isEqualTo(fieldName);
        assertTrue(dateException.getCause() instanceof IOException);

        verify(jsonGenerator).getOutputContext();
        verify(context).getCurrentName();
        verify(jsonGenerator).writeString(anyString());
    }
}