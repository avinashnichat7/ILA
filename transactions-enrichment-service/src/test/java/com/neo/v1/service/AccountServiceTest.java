package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.client.AccountClient;
import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.model.account.TransferFeesResponse;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.core.model.GenericRestParam.SourceType.TMSX;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.ACCOUNT_SERVICE_DOWN;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private static final String UNIT = "neo";
    private static final String SOURCE_TYPE = TMSX.getValue();
    private static String CUSTOMER_ID = "1234";
    private static String USER_ID = "test@test.com";

    @InjectMocks
    private AccountService subject;

    @Mock
    private AccountClient accountClient;

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
    void getFees_withTransferFeesRequest_returnTransferFeesResponse() {
        TransferFeesResponse expected = TransferFeesResponse.builder().build();
        when(accountClient.getTransferFees(any(), any(), any(), any(), any())).thenReturn(TransferFeesResponse.builder().build());
        TransferFeesResponse result = subject.getFees(TransferFeesRequest.builder().build());
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    void getFees_withTransferFeesRequest_ThrowException() {
        when(accountClient.getTransferFees(any(), any(), any(), any(), any())).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> getFeesResponse(subject)).getKeyMapping();
        assertEquals(ACCOUNT_SERVICE_DOWN, keyMapping);
    }

    private static TransferFeesResponse getFeesResponse(AccountService subject) {
        return subject.getFees(TransferFeesRequest.builder().build());
    }
}