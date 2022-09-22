package com.neo.v1.controller.advice;

import com.neo.core.builder.ErrorMetaInformationMapper;
import com.neo.core.message.GenericMessageSource;
import com.neo.core.model.ApiError;
import com.neo.core.model.ErrorMetaInformation;
import com.neo.openapi.utils.exception.NeoApiValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static com.neo.core.constants.CodeConstants.INVALID_DATE_CODE;
import static com.neo.core.constants.MessageConstants.INVALID_DATE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class TransactionEnrichmentControllerAdviceTest {

    @InjectMocks
    private TransactionEnrichmentControllerAdvice subject;

    @Mock
    private GenericMessageSource genericMessageSource;
    @Mock
    private ErrorMetaInformationMapper errorMetaInformationMapper;

    @Test
    void handleMethodArgumentTypeMismatchedException_MethodArgumentTypeMismatchExceptionAndWebRequest_ResponseEntity() {
        String wrongValue = "Invalid value";
        String invalidMessage = "invalid message";
        String name = "offset";

        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException =
                new MethodArgumentTypeMismatchException(wrongValue, LocalDate.class, name, mock(MethodParameter.class),
                        mock(NumberFormatException.class));
        ServletWebRequest mockWebRequest = mock(ServletWebRequest.class);
        ErrorMetaInformation errorMetaInformation = ErrorMetaInformation.builder()
                .message(invalidMessage)
                .code(INVALID_DATE_CODE)
                .build();

        ApiError apiError = ApiError.builder().errorMetaInformation(errorMetaInformation).build();
        ResponseEntity<ApiError> apiErrorResponseEntity = ResponseEntity.badRequest().body(apiError);
        TransactionEnrichmentControllerAdvice spySubject = Mockito.spy(subject);

        when(genericMessageSource.getMessage(INVALID_DATE_MESSAGE, name)).thenReturn(invalidMessage);
        when(errorMetaInformationMapper.map(INVALID_DATE_CODE, invalidMessage, null)).thenReturn(errorMetaInformation);

        doReturn(apiErrorResponseEntity).when(spySubject).buildResponseEntity(BAD_REQUEST, errorMetaInformation);

        ResponseEntity<Object> result = spySubject.handleMethodArgumentTypeMismatchedException(methodArgumentTypeMismatchException, mockWebRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(result.getBody()).isEqualToComparingFieldByField(apiError);

        verify(genericMessageSource).getMessage(INVALID_DATE_MESSAGE, name);
        verify(errorMetaInformationMapper).map(INVALID_DATE_CODE, invalidMessage, null);
    }

    @Test
    void handleHttpMessageNotReadable_NeoApiValidationExceptionAndWebRequest_ResponseEntity() {
        String invalidMessage = "invalid message";
        String name = "offset";

        NeoApiValidationException neoApiValidationException = new NeoApiValidationException(name, INVALID_DATE_CODE,
                INVALID_DATE_MESSAGE, mock(NumberFormatException.class));
        Exception exception = new Exception(neoApiValidationException);
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException(invalidMessage,
                exception, mock(HttpInputMessage.class));

        ServletWebRequest mockWebRequest = mock(ServletWebRequest.class);
        HttpServletRequest mockHttpServletRequest = mock(HttpServletRequest.class);

        when(mockWebRequest.getRequest()).thenReturn(mockHttpServletRequest);
        when(mockHttpServletRequest.getRequestURI()).thenReturn("www.abc.com");
        when(mockHttpServletRequest.getMethod()).thenReturn("POST");

        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);

        ErrorMetaInformation errorMetaInformation = ErrorMetaInformation.builder()
                .message(invalidMessage)
                .code(INVALID_DATE_CODE)
                .build();

        ApiError apiError = ApiError.builder().errorMetaInformation(errorMetaInformation).build();
        ResponseEntity<ApiError> apiErrorResponseEntity = ResponseEntity.badRequest().body(apiError);
        TransactionEnrichmentControllerAdvice spySubject = Mockito.spy(subject);

        when(errorMetaInformationMapper.map(neoApiValidationException.getCode(), neoApiValidationException.getUniversalMessage(), null)).thenReturn(errorMetaInformation);

        doReturn(apiErrorResponseEntity).when(spySubject).buildResponseEntity(BAD_REQUEST, errorMetaInformation);

        ResponseEntity<Object> result = spySubject.handleHttpMessageNotReadable(httpMessageNotReadableException,
                mockHttpHeaders, BAD_REQUEST, mockWebRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(BAD_REQUEST);

        verify(errorMetaInformationMapper).map(neoApiValidationException.getCode(), neoApiValidationException.getUniversalMessage(), null);
    }

    @Test
    void handleHttpMessageNotReadable_ExceptionAndWebRequest_ResponseEntity() {
        HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException("Error",
                mock(Exception.class), mock(HttpInputMessage.class));

        ServletWebRequest mockWebRequest = mock(ServletWebRequest.class);
        HttpHeaders mockHttpHeaders = mock(HttpHeaders.class);

        TransactionEnrichmentControllerAdvice spySubject = Mockito.spy(subject);

        ResponseEntity<Object> result = spySubject.handleHttpMessageNotReadable(httpMessageNotReadableException,
                mockHttpHeaders, BAD_REQUEST, mockWebRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
}