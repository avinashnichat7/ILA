package com.neo.v1.client.decoder;

import com.neo.core.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.CHARITY_SERVICE_ERROR;
import static com.neo.v1.util.TransactionEnrichmentErrorUtil.parseError;

public class CharityClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        parseError(methodKey, response);
        return new ServiceException(CHARITY_SERVICE_ERROR);
    }
}