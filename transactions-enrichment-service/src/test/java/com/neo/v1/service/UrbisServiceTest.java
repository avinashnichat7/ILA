package com.neo.v1.service;

import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.reader.PropertyReader;
import com.neo.v1.repository.urbis.TransactionsRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrbisServiceTest {

    private static final String CUSTOMER_ID = "12345678";

    @InjectMocks
    private UrbisService subject;

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private PropertyReader propertyReader;

    @Test
    void getAccountTransactions_ValidParameters_ListOfAccountTransactionEntity() {
        AccountTransactionsRequest request = new AccountTransactionsRequest();
        List<AccountTransactionEntity> expectation = Collections.emptyList();

        when(transactionsRepository.getAccountTransactions(CUSTOMER_ID, request)).thenReturn(expectation);

        List<AccountTransactionEntity> actual = subject.getAccountTransactions(CUSTOMER_ID, request);

        assertThat(actual).isEqualTo(expectation);

        verify(transactionsRepository).getAccountTransactions(CUSTOMER_ID, request);
    }


    @Test
    void getPendingAccountTransactions_ValidParameters_ListOfAccountTransactionEntity() {
        AccountTransactionsRequest request = new AccountTransactionsRequest();
        List<AccountPendingTransactionEntity> expectation = Collections.emptyList();

        when(propertyReader.getPendingTransactionsPageSize()).thenReturn(2);
        when(transactionsRepository.getPendingAccountTransactions(CUSTOMER_ID, request,2)).thenReturn(expectation);

        List<AccountPendingTransactionEntity> actual = subject.getPendingAccountTransactions(CUSTOMER_ID, request);

        assertThat(actual).isEqualTo(expectation);

        verify(propertyReader).getPendingTransactionsPageSize();
        verify(transactionsRepository).getPendingAccountTransactions(CUSTOMER_ID, request,2);
    }

}