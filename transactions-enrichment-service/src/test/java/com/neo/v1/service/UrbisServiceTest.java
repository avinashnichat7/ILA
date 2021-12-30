//package com.neo.v1.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
//import com.neo.v1.entity.urbis.AccountTransactionEntity;
//import com.neo.v1.reader.PropertyReader;
//import com.neo.v1.repository.urbis.TransactionsRepository;
//import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SuppressWarnings("ALL")
//@ExtendWith(SpringExtension.class)
////@ContextConfiguration(classes = {AppConfig.class, CreateHoldTransactionClientRequestMapper.class, UrbisPostTransactionRequestMapper.class, UrbisTransactionClientImpl.class, AdviceMapper.class})
//class UrbisServiceTest {
//
//    private static final String CUSTOMER_ID = "12345678";
//
//    @InjectMocks
//    private UrbisService subject;
//
//    @Mock
//    private PropertyReader propertyReader;
//
//    @Mock
//    private ObjectMapper mapper;
//
//    @SpyBean
//    @Qualifier("urbisObjectMapper")
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private TransactionsRepository transactionsRepository;
//
//    @Test
//    void getAccountTransactions_ValidParameters_ListOfAccountTransactionEntity() {
//        AccountTransactionsRequest request = new AccountTransactionsRequest();
//        List<AccountTransactionEntity> expectation = Collections.emptyList();
//
//        when(transactionsRepository.getAccountTransactions(CUSTOMER_ID, request)).thenReturn(expectation);
//
//        List<AccountTransactionEntity> actual = subject.getAccountTransactions(CUSTOMER_ID, request);
//
//        assertThat(actual).isEqualTo(expectation);
//
//        verify(transactionsRepository).getAccountTransactions(CUSTOMER_ID, request);
//    }
//
//
//    @Test
//    void getPendingAccountTransactions_ValidParameters_ListOfAccountTransactionEntity() {
//        AccountTransactionsRequest request = new AccountTransactionsRequest();
//        List<AccountPendingTransactionEntity> expectation = Collections.emptyList();
//
//        when(propertyReader.getPendingTransactionsPageSize()).thenReturn(2);
//        when(transactionsRepository.getPendingAccountTransactions(CUSTOMER_ID, request,2)).thenReturn(expectation);
//
//        List<AccountPendingTransactionEntity> actual = subject.getPendingAccountTransactions(CUSTOMER_ID, request);
//
//        assertThat(actual).isEqualTo(expectation);
//
//        verify(propertyReader).getPendingTransactionsPageSize();
//        verify(transactionsRepository).getPendingAccountTransactions(CUSTOMER_ID, request,2);
//    }
//
//}