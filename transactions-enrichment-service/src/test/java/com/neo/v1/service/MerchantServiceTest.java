package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.mapper.MerchantCategoryMapper;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.repository.CustomerAccountTransactionCategoryCustomRepository;
import com.neo.v1.repository.CustomerAccountTransactionCategoryRepository;
import com.neo.v1.repository.CustomerCategoryRepository;
import com.neo.v1.repository.CustomerMerchantCategoryRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @InjectMocks
    private MerchantService merchantService;

    @Mock
    private ProductCatalogueService productCatalogueService;
    @Mock
    private CustomerAccountTransactionCategoryCustomRepository customerAccountTransactionCategoryCustomRepository;
    @Mock
    private CustomerMerchantCategoryRepository customerMerchantCategoryRepository;
    @Mock
    private MerchantCategoryMapper merchantCategoryMapper;
    @Mock
    private CustomerAccountTransactionCategoryRepository customerAccountTransactionCategoryRepository;
    @Mock
    private CustomerCategoryRepository customerCategoryRepository;
    private static final String LANGUAGE = "en";
    private static final String UNIT = "neo";
    private static final String CUSTOMER_ID = "C1234";
    private static final String USER_ID = "U1234";

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
    void getCachedMerchantData_returnSuccess() {
        merchantService.clearCacheData();
        Map<String, MerchantDetail> expected = Collections.singletonMap("name", MerchantDetail.builder().name("name").build());
        when(productCatalogueService.getProductCatalogueMerchant()).thenReturn(Collections.singletonList(MerchantDetail.builder().name("name").build()));
        Map<String, MerchantDetail> result = merchantService.getCachedMerchantData();
        assertThat(expected.get("name")).isEqualToComparingFieldByField(result.get("name"));
    }

    @Test
    void getProductCatalogueMerchantCode_returnSuccess() {
        merchantService.clearCacheData();
        Map<String, MerchantCodeDetail> expected = Collections.singletonMap("code", MerchantCodeDetail.builder().code("code").build());
        when(productCatalogueService.getProductCatalogueMerchantCode()).thenReturn(Collections.singletonList(MerchantCodeDetail.builder().code("code").build()));
        Map<String, MerchantCodeDetail> result = merchantService.getProductCatalogueMerchantCode();
        assertThat(expected.get("code")).isEqualToComparingFieldByField(result.get("code"));
    }

    @Test
    void mapMerchantCategory_returnMerchantCategorySuccess() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id("1")
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        MerchantDetail merchantDetail = MerchantDetail.builder().build();
        Map<String, MerchantDetail> merchantDetailsCache = new HashMap<>();
        merchantDetailsCache.put("Other", merchantDetail);
        MerchantService.setCachedMerchantData(merchantDetailsCache);
        AccountTransaction accountTransaction = AccountTransaction.builder().merchantName("name").build();
        CustomerCategoryEntity customerCategory = CustomerCategoryEntity.builder().build();
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().transactionReference("Other").customerId(CUSTOMER_ID)
                .categoryId("1").isCustom(Boolean.TRUE).build();
        List<AccountTransaction> transactions = Collections.singletonList(accountTransaction);
        when(customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay()))
                .thenReturn(Collections.singletonList(customerAccountTransactionCategoryEntity));
        when(customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE))
                .thenReturn(Collections.singletonList(CustomerMerchantCategoryEntity.builder().name("name").categoryId("1").isCustom(Boolean.TRUE).build()));
        when(customerCategoryRepository.findByIdAndActive(1L, Boolean.TRUE)).thenReturn(customerCategory);
        merchantService.mapMerchantCategory(transactions, request);
        verify(merchantCategoryMapper).mapCustomCategory(accountTransaction, customerCategory);
    }

    @Test
    void mapMerchantCategory_returnAccountTransactionCategorySuccess() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id("1")
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        MerchantDetail merchantDetail = MerchantDetail.builder().build();
        Map<String, MerchantDetail> merchantDetailsCache = new HashMap<>();
        merchantDetailsCache.put("name", merchantDetail);
        MerchantService.setCachedMerchantData(merchantDetailsCache);
        AccountTransaction accountTransaction = AccountTransaction.builder().merchantName("name").build();
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().transactionReference("Other").customerId(CUSTOMER_ID)
                .categoryId("1").isCustom(Boolean.TRUE).build();
        List<AccountTransaction> transactions = Collections.singletonList(accountTransaction);
        when(customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay()))
                .thenReturn(Collections.singletonList(customerAccountTransactionCategoryEntity));
        when(customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE))
                .thenReturn(Collections.singletonList(CustomerMerchantCategoryEntity.builder().name("name").categoryId("1").isCustom(Boolean.FALSE).build()));
        merchantService.mapMerchantCategory(transactions, request);
        verify(merchantCategoryMapper).mapAccountTransactionCategory(accountTransaction, merchantDetail);
    }

    @Test
    void mapMerchantCategory_returnSuccess() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id("1")
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        MerchantDetail merchantDetail = MerchantDetail.builder().build();
        Map<String, MerchantDetail> merchantDetailsCache = new HashMap<>();
        merchantDetailsCache.put("Other", merchantDetail);
        MerchantService.setCachedMerchantData(merchantDetailsCache);
        AccountTransaction accountTransaction = AccountTransaction.builder().merchantName("name123").build();
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().transactionReference("Other").customerId(CUSTOMER_ID)
                .categoryId("1").build();
        List<AccountTransaction> transactions = Collections.singletonList(accountTransaction);
        when(customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay()))
                .thenReturn(Collections.singletonList(customerAccountTransactionCategoryEntity));
        when(customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE))
                .thenReturn(Collections.singletonList(CustomerMerchantCategoryEntity.builder().name("name").build()));
        merchantService.mapMerchantCategory(transactions, request);
        verify(merchantCategoryMapper).mapAccountTransactionCategory(accountTransaction, merchantDetail);
    }

    @Test
    void mapMerchantCategory_returnCustomerAccountTransactionCategoryEntity() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id("1")
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        CustomerCategoryEntity customerCategory = CustomerCategoryEntity.builder().build();
        AccountTransaction accountTransaction = AccountTransaction.builder().merchantName("name123").reference("Other").build();
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().transactionReference("Other").customerId(CUSTOMER_ID)
                .categoryId("1").isCustom(Boolean.TRUE).build();
        List<AccountTransaction> transactions = Collections.singletonList(accountTransaction);
        when(customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay()))
                .thenReturn(Collections.singletonList(customerAccountTransactionCategoryEntity));
        when(customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE))
                .thenReturn(Collections.singletonList(CustomerMerchantCategoryEntity.builder().name("name").build()));
        when(customerCategoryRepository.findByIdAndActive(1L, Boolean.TRUE)).thenReturn(customerCategory);
        merchantService.mapMerchantCategory(transactions, request);
        verify(merchantCategoryMapper).mapCustomCategory(accountTransaction, customerCategory);
    }

    @Test
    void mapMerchantCategory_returnCustomCustomerAccountTransactionCategoryEntity() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("pending")
                .id("1")
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        MerchantDetail merchantDetail = MerchantDetail.builder().build();
        Map<String, MerchantDetail> merchantDetailsCache = new HashMap<>();
        merchantDetailsCache.put("name", merchantDetail);
        MerchantService.setCachedMerchantData(merchantDetailsCache);
        AccountTransaction accountTransaction = AccountTransaction.builder().merchantName("name").reference("Other").build();
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().transactionReference("Other").customerId(CUSTOMER_ID)
                .categoryId("1").isCustom(Boolean.FALSE).build();
        List<AccountTransaction> transactions = Collections.singletonList(accountTransaction);
        when(customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay()))
                .thenReturn(Collections.singletonList(customerAccountTransactionCategoryEntity));
        when(customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE))
                .thenReturn(Collections.singletonList(CustomerMerchantCategoryEntity.builder().name("name").build()));
        merchantService.mapMerchantCategory(transactions, request);
        verify(merchantCategoryMapper).mapAccountTransactionCategory(accountTransaction, merchantDetail);
    }

    @Test
    void updateMerchantDetailsCache_returnSuccess() {
        Map<String, MerchantDetail> merchantDetailsCache = new HashMap<>();
        Map<String, MerchantCodeDetail> merchantCodeDetailsCache = new HashMap<>();
        merchantDetailsCache.put("1", MerchantDetail.builder().build());
        merchantCodeDetailsCache.put("1", MerchantCodeDetail.builder().build());
        MerchantService.setMerchantCodeDetailsCache(merchantCodeDetailsCache);
        MerchantService.setCachedMerchantData(merchantDetailsCache);
        merchantService.clearCacheData();
        System.out.println(merchantDetailsCache.isEmpty());
        Assertions.assertThat(merchantDetailsCache).isEmpty();
        Assertions.assertThat(merchantCodeDetailsCache).isEmpty();
    }

    @Test
    void saveCustomerAccountTransactionCategory_returnSuccess() {
        CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = CustomerAccountTransactionCategoryEntity.builder().build();
        merchantService.saveOrUpdateCustomerAccountTransactionCategory(customerAccountTransactionCategoryEntity);
        verify(customerAccountTransactionCategoryRepository).save(customerAccountTransactionCategoryEntity);
    }

    @Test
    void saveCustomerMerchantCategory_returnSuccess() {
        CustomerMerchantCategoryEntity customerMerchantCategoryEntity = CustomerMerchantCategoryEntity.builder().build();
        merchantService.saveOrUpdateCustomerMerchantCategory(customerMerchantCategoryEntity);
        verify(customerMerchantCategoryRepository).save(customerMerchantCategoryEntity);
    }

}
