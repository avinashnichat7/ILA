package com.neo.v1.client.decoder;

import com.neo.core.exception.ServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE;
import static com.neo.v1.util.TransactionEnrichmentErrorUtil.parseError;

public class ProductCatalogueClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        parseError(methodKey, response);
        return new ServiceException(PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE);
    }
}