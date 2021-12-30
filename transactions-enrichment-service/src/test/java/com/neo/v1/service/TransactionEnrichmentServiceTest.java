package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.mapper.AccountTransactionsMapper;
import com.neo.v1.mapper.AccountTransactionsResponseMapper;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionEnrichmentServiceTest {

    @InjectMocks
    private  TransactionEnrichmentService subject;

    private static String ID = "123456789";
    private static String OPERTATION_TYPE = "EFT-NRT";
    private static final String LANGUAGE = "en";
    private static final String UNIT = "neo";
    private static final String CUSTOMER_ID = "C1234";
    private static final String USER_ID = "U1234";
    @Mock
    private AccountService accountService;

    @Mock
    private TransactionPaginationService transactionPaginationService;

    @Mock
    private AccountTransactionsMapper accountTransactionsMapper;
    @Mock
    private AccountTransactionsResponseMapper accountTransactionsResponseMapper;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UrbisService urbisService;

    @BeforeEach
    void before() {
        GenericRestParamDto genericRestParamDto = GenericRestParamDto.builder()
                .locale(new Locale(LANGUAGE))
                .unit(UNIT)
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .build();
        GenericRestParamContextHolder.setContext(genericRestParamDto);
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountFailedPendingTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsData() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("failed_pending")
                .id(ID)
                .pageSize(new Long(1))
                .offset(new Long(1))
                .build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransaction accountTransaction = AccountTransaction.builder().originalAmount(positiveAmount).amount(positiveAmount).build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder().build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(transactionPaginationService.getPaginatedRecords(any(List.class), eq(1), eq(1))).thenReturn(Arrays.asList(accountTransaction));
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
        verify(transactionPaginationService).getPaginatedRecords(any(List.class), eq(1), eq(1));
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountPendingTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsData() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id(ID)
                .pageSize(new Long(1))
                .offset(new Long(1))
                .build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransaction accountTransaction = AccountTransaction.builder().originalAmount(positiveAmount).amount(positiveAmount).build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder().build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(transactionPaginationService.getPaginatedRecords(any(List.class), eq(1), eq(1))).thenReturn(Arrays.asList(accountTransaction));
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
        verify(transactionPaginationService).getPaginatedRecords(any(List.class), eq(1), eq(1));
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountFailedTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsData() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("failed")
                .id(ID)
                .pageSize(new Long(1))
                .offset(new Long(1))
                .build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransaction accountTransaction = AccountTransaction.builder().originalAmount(positiveAmount).amount(positiveAmount).build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder().build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(transactionPaginationService.getPaginatedRecords(any(List.class), eq(1), eq(1))).thenReturn(Arrays.asList(accountTransaction));
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
        verify(transactionPaginationService).getPaginatedRecords(any(List.class), eq(1), eq(1));
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountCompletedTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsData() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("completed")
                .id(ID)
                .pageSize(new Long(1))
                .offset(new Long(1))
                .build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder().build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
    }
}
