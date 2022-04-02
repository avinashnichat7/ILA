package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.client.TransactionClient;
import com.neo.v1.mapper.TransactionDetailRequestMapper;
import com.neo.v1.mapper.TransactionsRequestMapper;
import com.neo.v1.model.transactions.TransactionDetailResponse;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import feign.RetryableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.neo.core.model.GenericRestParam.SourceType.TMSX;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.TRANSACTION_SERVICE_UNAVAILABLE;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final String UNIT = "neo";
    private static String CUSTOMER_ID = "1234";
    private static String USER_ID = "test@test.com";

    @InjectMocks
    private TransactionService subject;

    @Mock
    private TransactionClient transactionClient;

    @Mock
    private TransactionsRequestMapper transactionsRequestMapper;

    @Mock
    private TransactionDetailRequestMapper transactionDetailRequestMapper;

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
    void getAccountTransactionsByStatus_withAccountTransactionsRequestAndStatus_returnAccountTransactionList() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder().build();
        String status = "status";
        List<AccountTransaction> expected = Collections.singletonList(AccountTransaction.builder().build());
        when(transactionClient.getAccountTransactionsByStatus(any(), any(), any(), any(), any(), any()))
                .thenReturn(AccountTransactionsResponse.builder().data(AccountTransactions.builder()
                        .transactions(Collections.singletonList(AccountTransaction.builder().build())).build()).build());
        List<AccountTransaction> result = subject.getAccountTransactionsByStatus(request, status);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expected.get(0));
    }

    @Test
    void getAccountTransactionsByStatus_withAccountTransactionsRequestAndStatus_ThrowException() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder().build();
        String status = "status";
        when(transactionClient.getAccountTransactionsByStatus(any(), any(), any(), any(), any(), any())).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getAccountTransactionsByStatus(request, status)).getKeyMapping();
        Assertions.assertEquals(TRANSACTION_SERVICE_UNAVAILABLE, keyMapping);
    }

    @Test
    void getTransactionDetail_returnAccountTransaction() {
        String accountId = "accountId";
        String transactionReference = "reference";
        AccountTransaction expected = AccountTransaction.builder().build();
        when(transactionClient.getTransactionDetail(any(), any(), any(), any(), any()))
                .thenReturn(TransactionDetailResponse.builder().data(AccountTransaction.builder().build()).build());
        AccountTransaction result = subject.getTransactionDetail(accountId, transactionReference);
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    void getTransactionDetail_ThrowException() {
        String accountId = "accountId";
        String transactionReference = "reference";
        when(transactionClient.getTransactionDetail(any(), any(), any(), any(), any())).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getTransactionDetail(accountId, transactionReference)).getKeyMapping();
        Assertions.assertEquals(TRANSACTION_SERVICE_UNAVAILABLE, keyMapping);
    }

}