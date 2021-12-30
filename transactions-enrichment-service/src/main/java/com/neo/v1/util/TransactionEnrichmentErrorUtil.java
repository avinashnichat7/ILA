package com.neo.v1.util;

import com.neo.core.utils.HeaderUtils;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
public final class TransactionEnrichmentErrorUtil {

    private TransactionEnrichmentErrorUtil() {
    }

    public static void parseError(String methodKey, Response response) {
        log.error("Http Status : {}", response.status());
        log.error("request headers : [{}]", HeaderUtils.maskHeaderValues(response.request().headers()));
        log.error("response headers : [{}]", response.headers());
        log.error("error occurred while executing: [{}] on request url : [{}]", methodKey, response.request().url());
        log.error("error response received : [{}]", getErrorResponse(response));
    }

    private static String getErrorResponse(Response response) {
        String errorResponse = EMPTY;
        try {
            Response.Body responseBody = response.body();
            errorResponse = (null != responseBody) ? IOUtils.toString(responseBody.asInputStream(), UTF_8) : "response body is null";
        } catch (IOException ioException) {
            log.error("unable to parse error response", ioException);
        }
        return errorResponse;
    }
}