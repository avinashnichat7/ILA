package com.neo.v1.controller.advice;

import com.neo.core.builder.ErrorMetaInformationMapper;
import com.neo.core.exception.handler.GenericResponseEntityExceptionHandler;
import com.neo.core.message.GenericMessageSource;
import com.neo.core.model.ErrorMetaInformation;
import com.neo.openapi.utils.exception.NeoApiValidationException;
import com.neo.v1.controller.TransactionEnrichmentController;
import joptsimple.internal.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.neo.core.constants.CodeConstants.INTERNAL_SERVER_ERROR_CODE;
import static com.neo.core.constants.CodeConstants.INVALID_DATE_CODE;
import static com.neo.core.constants.MessageConstants.INVALID_DATE_MESSAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice(assignableTypes = TransactionEnrichmentController.class)
@RequiredArgsConstructor
public class TransactionEnrichmentControllerAdvice extends GenericResponseEntityExceptionHandler {

    private final GenericMessageSource genericMessageSource;
    private final ErrorMetaInformationMapper errorMetaInformationMapper;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchedException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("MissingServletRequestParameterException exception occurred: ", ex);
        String errorCode = INTERNAL_SERVER_ERROR_CODE;
        String message = null;
        if (Objects.nonNull(ex) && isDateException(Optional.ofNullable(ex).map(TypeMismatchException::getRequiredType).map(Class::getName).orElse(Strings.EMPTY))) {
            errorCode = INVALID_DATE_CODE;
            message = genericMessageSource.getMessage(INVALID_DATE_MESSAGE, ex.getName());
        }
        ErrorMetaInformation errorMeta = errorMetaInformationMapper.map(errorCode, message, null);
        return buildResponseEntity(BAD_REQUEST, errorMeta);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause().getCause() instanceof NeoApiValidationException) {
            NeoApiValidationException neoApiValidationException = (NeoApiValidationException) ex.getCause().getCause();
            log.error("Generic exception occurred: " + ex.getMessage(), ex);
            HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
            log.info(String.join(" ", httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), neoApiValidationException.toString(), ex.getMessage()));
            ErrorMetaInformation errorMeta = this.errorMetaInformationMapper.map(neoApiValidationException.getCode(), neoApiValidationException.getUniversalMessage(), null);
            return this.buildResponseEntity(HttpStatus.BAD_REQUEST, errorMeta);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    private boolean isDateException(String requiredTypeClassName) {
        return requiredTypeClassName.equalsIgnoreCase(LocalDate.class.getName());
    }
}
