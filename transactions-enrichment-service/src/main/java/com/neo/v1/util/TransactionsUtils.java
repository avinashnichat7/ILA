package com.neo.v1.util;

import com.neo.core.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.FILTER_DECODING_ERROR;


public class TransactionsUtils {

    private TransactionsUtils(){}

    public static String decodeString(String encodedString) {
        try {
            return StringUtils.isNotBlank(encodedString)
                    ? URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString())
                    : encodedString;
        } catch (Exception ex) {
            throw new ServiceException(FILTER_DECODING_ERROR, ex);
        }
    }
}
