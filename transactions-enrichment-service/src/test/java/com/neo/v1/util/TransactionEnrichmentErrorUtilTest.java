package com.neo.v1.util;

import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import static java.lang.reflect.Modifier.isPrivate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class TransactionEnrichmentErrorUtilTest {

    @Test
    void plaErrorUtil_withPrivateConstructor_checkForPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<TransactionEnrichmentErrorUtil> constructor = TransactionEnrichmentErrorUtil.class.getDeclaredConstructor();
        assertThat(isPrivate(constructor.getModifiers())).isTrue();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void parseError_withMethodKeyAndResponse_subjectToExecute() throws IOException {
        String methodKey = "method key";
        Response response = mock(Response.class);
        Response.Body body = mock(Response.Body.class);
        InputStream is = mock(InputStream.class);
        Request request = mock(Request.class);
        int status = BAD_REQUEST.value();

        when(response.status()).thenReturn(status);
        when(response.body()).thenReturn(body);
        when(response.request()).thenReturn(request);
        when(body.asInputStream()).thenReturn(is);
        when(request.headers()).thenReturn(Collections.emptyMap());
        when(response.headers()).thenReturn(Collections.emptyMap());

        TransactionEnrichmentErrorUtil.parseError(methodKey, response);

        verify(response).body();
        verify(response, times(2)).request();
        verify(body).asInputStream();
        verify(request).headers();
        verify(response).headers();
    }

    @Test
    void parseError_withMethodKeyAndResponseWithNullBody_subjectToExecute() {
        String methodKey = "method key";
        Response response = mock(Response.class);
        Request request = mock(Request.class);
        int status = BAD_REQUEST.value();

        when(response.status()).thenReturn(status);
        when(response.request()).thenReturn(request);
        when(request.headers()).thenReturn(Collections.emptyMap());
        when(response.headers()).thenReturn(Collections.emptyMap());

        TransactionEnrichmentErrorUtil.parseError(methodKey, response);

        verify(response).body();
        verify(response, times(2)).request();
        verify(request).headers();
        verify(response).headers();
    }

    @Test
    void parseError_withMethodKeyAndResponse_throwException() throws IOException {
        String methodKey = "method key";
        Response response = mock(Response.class);
        Response.Body body = mock(Response.Body.class);
        Request request = mock(Request.class);
        int status = BAD_REQUEST.value();

        when(response.status()).thenReturn(status);
        when(response.body()).thenReturn(body);
        when(response.request()).thenReturn(request);
        when(body.asInputStream()).thenThrow(new IOException());
        when(request.headers()).thenReturn(Collections.emptyMap());
        when(response.headers()).thenReturn(Collections.emptyMap());

        TransactionEnrichmentErrorUtil.parseError(methodKey, response);

        verify(response).body();
        verify(response, times(2)).request();
        verify(body).asInputStream();
        verify(request).headers();
        verify(response).headers();
    }
}
