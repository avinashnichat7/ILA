package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.CreditCardClient;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsResponse;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.CREDIT_CARD_SERVICE_DOWN;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardService {

    private final CreditCardClient creditCardClient;

    CreditCardTransactionsResponse postCreditCardsTransactions(CreditCardTransactionsRequest creditCardTransactionsRequest) {
        CreditCardTransactionsResponse creditCardTransactionsResponse;
        try {
            creditCardTransactionsResponse = creditCardClient.postCreditCardsTransactions(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getUnit(),
                    getContext().getLocale().getLanguage(),
                    creditCardTransactionsRequest);
        } catch (RetryableException retryableException) {
            throw new ServiceException(CREDIT_CARD_SERVICE_DOWN, retryableException);
        }
        return creditCardTransactionsResponse;
    }
}
