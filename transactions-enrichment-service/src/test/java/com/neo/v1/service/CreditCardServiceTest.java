package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.client.CreditCardClient;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsResponse;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.core.model.GenericRestParam.SourceType.TMSX;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.CREDIT_CARD_SERVICE_DOWN;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    private static final String UNIT = "neo";
    private static final String SOURCE_TYPE = TMSX.getValue();
    private static String CUSTOMER_ID = "1234";
    private static String USER_ID = "test@test.com";

    @InjectMocks
    private CreditCardService subject;

    @Mock
    private CreditCardClient creditCardClient;

    @BeforeEach
    void setupContext() {
        GenericRestParamContextHolder.setContext(GenericRestParamDto.builder()
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .locale(ENGLISH)
                .unit(UNIT)
                .source(TMSX)
                .build());
    }

    @Test
    void postCreditCardsTransactions_withTransferFeesRequest_returnTransferFeesResponse() {
        CreditCardTransactionsResponse expected = CreditCardTransactionsResponse.builder().build();
        when(creditCardClient.postCreditCardsTransactions(any(), any(), any(), any(), any())).thenReturn(CreditCardTransactionsResponse.builder().build());
        CreditCardTransactionsResponse result = subject.postCreditCardsTransactions(CreditCardTransactionsRequest.builder().build());
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    void postCreditCardsTransactions_withTransferFeesRequest_ThrowException() {
        when(creditCardClient.postCreditCardsTransactions(any(), any(), any(), any(), any())).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.postCreditCardsTransactions(CreditCardTransactionsRequest.builder().build())).getKeyMapping();
        assertEquals(CREDIT_CARD_SERVICE_DOWN, keyMapping);
    }

}