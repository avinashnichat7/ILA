package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.dispute.model.DisputeCaseObject;
import com.neo.v1.dispute.model.ListOfDisputeCasesDisput;
import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionHoldEntity;
import com.neo.v1.enums.customer.RecordType;
import com.neo.v1.mapper.AccountTransactionHoldMapper;
import com.neo.v1.mapper.AccountTransactionsMapper;
import com.neo.v1.mapper.AccountTransactionsResponseMapper;
import com.neo.v1.mapper.CreateCategoryResponseMapper;
import com.neo.v1.mapper.CustomerAccountTransactionCategoryEntityMapper;
import com.neo.v1.mapper.CustomerCategoryMapper;
import com.neo.v1.mapper.CustomerMerchantCategoryEntityMapper;
import com.neo.v1.mapper.DisputeTransactionMapper;
import com.neo.v1.mapper.MetaMapper;
import com.neo.v1.mapper.TransferFeesRequestMapper;
import com.neo.v1.mapper.UpdateCategoryResponseMapper;
import com.neo.v1.model.account.TransferCharge;
import com.neo.v1.model.account.TransferFees;
import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.model.account.TransferFeesResponse;
import com.neo.v1.model.customer.CustomerDetailData;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.repository.CustomerCategoryRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionHold;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsData;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.Currency;
import com.neo.v1.transactions.enrichment.model.DeleteCategoryResponse;
import com.neo.v1.transactions.enrichment.model.Dispute;
import com.neo.v1.transactions.enrichment.model.TransactionHoldRequest;
import com.neo.v1.transactions.enrichment.model.TransactionHoldResponse;
import com.neo.v1.transactions.enrichment.model.TransactionHoldResponseData;
import com.neo.v1.transactions.enrichment.model.TransactionLinkRequest;
import com.neo.v1.transactions.enrichment.model.TransactionLinkResponse;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.constants.TransactionEnrichmentConstants.MERCHANT;
import static com.neo.v1.constants.TransactionEnrichmentConstants.REFERENCE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_HOLD_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_HOLD_SUCCESS_MSG;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_CATEGORY;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_COLOR;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_ICON;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_CATEGORY;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_COLOR;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_ICON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class TransactionEnrichmentServiceTest {

    @InjectMocks
    private  TransactionEnrichmentService subject;

    private static final Integer GENERATE_ADVICE = 1;
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

    @Mock
    private ProductCatalogueService productCatalogueService;

    @Mock
    private CustomerCategoryRepository customerCategoryRepository;

    @Mock
    private CustomerCategoryMapper customerCategoryMapper;

    @Mock
    private CustomerService customerService;

    @Mock
    private CreateCategoryResponseMapper createCategoryResponseMapper;

    @Mock
    private MetaMapper metaMapper;

    @Mock
    private UpdateCategoryResponseMapper updateCategoryResponseMapper;

    @Mock
    private MerchantService merchantService;

    @Mock
    private CustomerAccountTransactionCategoryEntityMapper customerAccountTransactionCategoryEntityMapper;

    @Mock
    private CustomerMerchantCategoryEntityMapper customerMerchantCategoryEntityMapper;
    
    @Mock
    private AccountTransactionHoldMapper accountTransactionHoldMapper;
    @Mock
    private CreditCardService creditCardService;

    @Mock
    private TransferFeesRequestMapper transferFeesRequestMapper;

    @Mock
    private DisputeTransactionMapper disputeTransactionMapper;
    @Mock
    private DisputeTransactionService disputeTransactionService;
    @Mock
    private DisputeService disputeService;

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
    @DisplayName("Get accounts pending transactions test")
    void getAccountTransactions_ForPendingTransactions_returnSuccess() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        String failedPending = "pending";
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status(failedPending)
                .id(ID)
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransaction accountTransaction = AccountTransaction.builder().originalAmount(positiveAmount).amount(positiveAmount).transactionType("EFT-CSCT-DNS").build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder().build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(transactionService.getAccountTransactionsByStatus(request, failedPending)).thenReturn(Arrays.asList(accountTransaction));
        when(accountService.getFees(any())).thenReturn(TransferFeesResponse.builder().data(TransferFees.builder().charges(Collections.singletonList(TransferCharge.builder().chargeAmountInAccountCurrency(BigDecimal.TEN).build())).build()).build());
        when(transactionPaginationService.getPaginatedRecords(any(List.class), eq(1), eq(1))).thenReturn(Arrays.asList(accountTransaction));
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
        verify(transactionPaginationService).getPaginatedRecords(any(List.class), eq(1), eq(1));
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountFailedPendingTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsData() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        String failedPending = "failed_pending";
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status(failedPending)
                .id(ID)
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
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
        String pending = "pending";
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status(pending)
                .id(ID)
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
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
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
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
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
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

    @Test
    void getMerchantCategoryList_returnSuccess() {
        CategoryListResponse expected = CategoryListResponse.builder().build();
        CategoryDetail categoryDetail = CategoryDetail.builder().id("1").name("category1").build();
        CustomerCategoryEntity customCategoryDetail = CustomerCategoryEntity.builder().id(12l).name("category2").build();
        when(productCatalogueService.getProductCatalogueMerchantCategory()).thenReturn(Collections.singletonList(categoryDetail));
        when(customerCategoryRepository.findByCustomerIdAndActive(any(), anyBoolean())).thenReturn(Collections.singletonList(customCategoryDetail));
        when(customerCategoryMapper.map(Collections.singletonList(categoryDetail), Collections.singletonList(customCategoryDetail)))
                .thenReturn(expected);
        CategoryListResponse result = subject.getMerchantCategoryList();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void createCategory_returnThrowInvalidNameException() {
        String icon = "icon";
        String color = "color";
        CreateCategoryRequest req = CreateCategoryRequest.builder().icon(icon).color(color).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.createCategory(req)).getKeyMapping();
        Assertions.assertEquals(INVALID_CATEGORY, keyMapping);
    }

    @Test
    void createCategory_returnSuccess() {
        String name = "name";
        String icon = "icon";
        String color = "color";
        String customerId = "customerId";
        CustomerDetailData customerDetailData = CustomerDetailData.builder().customerId(customerId).recordType(RecordType.CUSTOMER).build();
        CreateCategoryRequest req = CreateCategoryRequest.builder().name(name).icon(icon).color(color).build();
        CustomerCategoryEntity customerCategoryEntity = CustomerCategoryEntity.builder().build();
        when(customerService.getCustomerDetail()).thenReturn(customerDetailData);
        when(customerCategoryMapper.map(req, customerDetailData.getCustomerId())).thenReturn(customerCategoryEntity);
        when(customerCategoryRepository.save(customerCategoryEntity)).thenReturn(CustomerCategoryEntity.builder().build());
        when(createCategoryResponseMapper.map(any(), any())).thenReturn(CreateCategoryResponse.builder().build());
        CreateCategoryResponse expected = CreateCategoryResponse.builder().build();
        CreateCategoryResponse result = subject.createCategory(req);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void createCategory_returnThrowInvalidIconException() {
        String name = "name";
        String color = "color";
        CreateCategoryRequest req = CreateCategoryRequest.builder().name(name).color(color).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.createCategory(req)).getKeyMapping();
        Assertions.assertEquals(INVALID_ICON, keyMapping);
    }

    @Test
    void createCategory_returnThrowInvalidColorException() {
        String name = "name";
        String icon = "icon";
        CreateCategoryRequest req = CreateCategoryRequest.builder().icon(icon).name(name).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.createCategory(req)).getKeyMapping();
        Assertions.assertEquals(INVALID_COLOR, keyMapping);
    }

    @Test
    void updateCategory_returnSuccess() {
        String name = "name";
        String icon = "icon";
        String color = "color";
        Long categoryId = 1L;
        UpdateCategoryResponse expected = UpdateCategoryResponse.builder().build();
        CustomerCategoryEntity categoryEntity = CustomerCategoryEntity.builder().build();
        UpdateCategoryRequest req = UpdateCategoryRequest.builder().name(name).icon(icon).color(color).build();
        when(customerCategoryRepository.findByIdAndCustomerIdAndActive(categoryId, CUSTOMER_ID, Boolean.TRUE)).thenReturn(Optional.of(categoryEntity));
        when(customerCategoryMapper.map(req, categoryEntity.getCustomerId(), categoryId)).thenReturn(CustomerCategoryEntity.builder().build());
        when(updateCategoryResponseMapper.map(any(), any())).thenReturn(expected);
        UpdateCategoryResponse result = subject.updateCategory(categoryId, req);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void updateCategory_throwServiceException() {
        String icon = "icon";
        String color = "color";
        Long categoryId = 1L;
        UpdateCategoryRequest req = UpdateCategoryRequest.builder().icon(icon).color(color).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.updateCategory(categoryId, req)).getKeyMapping();
        Assertions.assertEquals(UPDATE_CATEGORY_INVALID_CATEGORY, keyMapping);
    }

    @Test
    void updateCategory_throwInvalidColorException() {
        String icon = "icon";
        String name = "name";
        Long categoryId = 1L;
        UpdateCategoryRequest req = UpdateCategoryRequest.builder().icon(icon).name(name).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.updateCategory(categoryId, req)).getKeyMapping();
        Assertions.assertEquals(UPDATE_CATEGORY_INVALID_COLOR, keyMapping);
    }

    @Test
    void updateCategory_throwInvalidIconException() {
        String color = "color";
        String name = "name";
        Long categoryId = 1L;
        UpdateCategoryRequest req = UpdateCategoryRequest.builder().color(color).name(name).build();
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.updateCategory(categoryId, req)).getKeyMapping();
        Assertions.assertEquals(UPDATE_CATEGORY_INVALID_ICON, keyMapping);
    }

    @Test
    void deleteCategory_returnSuccess() {
        Long categoryId = 1L;
        DeleteCategoryResponse expected = DeleteCategoryResponse.builder().build();
        when(customerCategoryRepository.findByIdAndCustomerIdAndActive(categoryId, CUSTOMER_ID, Boolean.TRUE)).thenReturn(Optional.of(CustomerCategoryEntity.builder().build()));
        DeleteCategoryResponse result = subject.deleteCategory(categoryId);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
        verify(customerCategoryRepository).save(any());
    }

    @Test
    void link_withLinkTypeReference_returnTransactionLinkResponse() {
        TransactionLinkRequest request = TransactionLinkRequest.builder()
                .iban("iban")
                .categoryId("1")
                .linkType(REFERENCE)
                .transactionReference("reference")
                .build();
        TransactionLinkResponse expected = TransactionLinkResponse.builder().build();
        when(transactionService.getTransactionDetail(request.getIban(), request.getTransactionReference())).thenReturn(AccountTransaction.builder().build());
        when(customerAccountTransactionCategoryEntityMapper.map(any(), any(), any(), anyBoolean())).thenReturn(CustomerAccountTransactionCategoryEntity.builder().build());
        when(customerCategoryRepository.findByIdAndActive(1L, Boolean.TRUE)).thenReturn(CustomerCategoryEntity.builder().build());
        TransactionLinkResponse result = subject.link(request);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void link_withLinkTypeMerchant_returnTransactionLinkResponse() {
        TransactionLinkRequest request = TransactionLinkRequest.builder()
                .iban("iban")
                .categoryId("1")
                .linkType(MERCHANT)
                .transactionReference("reference")
                .build();
        TransactionLinkResponse expected = TransactionLinkResponse.builder().build();
        when(transactionService.getTransactionDetail(request.getIban(), request.getTransactionReference())).thenReturn(AccountTransaction.builder().build());
        when(customerMerchantCategoryEntityMapper.map(any(), any(), anyBoolean())).thenReturn(CustomerMerchantCategoryEntity.builder().build());
        when(customerCategoryRepository.findByIdAndActive(1L, Boolean.TRUE)).thenReturn(CustomerCategoryEntity.builder().build());
        TransactionLinkResponse result = subject.link(request);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
    
    @Test
    void hold_withTransactionHoldRequest_returnTransactionHoldResponse() {
    	String transactionCurrencyCode = "XXXX";
        String transactionCurrencyPlaces = "1";
        String accountCurrencyCode = "XXXX";
        String accountCurrencyPlaces = "1";
        String id = "XXXX";
        LocalDateTime transactionDate = LocalDateTime.now();
        LocalDate valueDate = LocalDate.now();
        String transactionType = "XXXX";
        BigDecimal transactionExchangeRate = BigDecimal.valueOf(3);
        String transactionDescription1 = "XXXX";
        String transactionDescription2 = "XXXX";
        String transactionDescription3 = "XXXX";
        String transactionDescription4 = "XXXX";
        String transactionDescription5 = "XXXX";
        String transactionDescription6 = "XXXX";
        BigDecimal amount = BigDecimal.valueOf(300.6834);
        BigDecimal originalAmount = BigDecimal.valueOf(400.7134);
        String reference = "XXXX";
        BigDecimal previousBalance = BigDecimal.valueOf(598.900);
        BigDecimal currentBalance = BigDecimal.valueOf(400.89);
        Integer generateAdvice = 1;
        String status = "completed";
        
        Currency accountCurrency = Currency.builder()
                .code(accountCurrencyCode)
                .decimalPlaces(accountCurrencyPlaces)
                .build();

    	TransactionHoldRequest request = TransactionHoldRequest.builder()
                .build();
        AccountTransactionHold accountTransactionHold = AccountTransactionHold.builder()
                .id(id)
                .holdDate(transactionDate)
                .holdExpiryDate(valueDate)
                .holdType(transactionType)
                .holdCurrency(accountCurrency)
                .holdDescription1(transactionDescription1)
                .holdDescription2(transactionDescription2)
                .holdDescription3(transactionDescription3)
                .holdDescription4(transactionDescription4)
                .holdDescription5(transactionDescription5)
                .holdDescription6(transactionDescription6)
                .holdAmount(amount.setScale(Integer.parseInt(transactionCurrencyPlaces), BigDecimal.ROUND_HALF_UP))
                .holdReferenceNumber(reference)
                .previousBalance(previousBalance)
                .currentBalance(currentBalance)
                .generateAdvice(GENERATE_ADVICE.equals(generateAdvice))
                .build();
        TransactionHoldResponseData transactionHoldResponseData = TransactionHoldResponseData.builder().transactions(Collections.singletonList(accountTransactionHold)).build();
        TransactionHoldResponse expected = TransactionHoldResponse.builder()
        		.data(transactionHoldResponseData)
        		.meta(metaMapper.map(TRANSACTION_HOLD_SUCCESS_CODE, TRANSACTION_HOLD_SUCCESS_MSG))
        		.build();
 
        AccountTransactionHoldEntity accountTransactionHoldEntity = AccountTransactionHoldEntity.builder()
                .id(id)
                .transactionDate(transactionDate)
                .valueDate(valueDate)
                .holdExpiryDate(valueDate)
                .transactionType(transactionType)
                .transactionCurrency(transactionCurrencyCode)
                .transactionCurrencyPlaces(transactionCurrencyPlaces)
                .transactionExchangeRate(transactionExchangeRate)
                .accountCurrency(accountCurrencyCode)
                .accountCurrencyPlaces(accountCurrencyPlaces)
                .transactionDescription1(transactionDescription1)
                .transactionDescription2(transactionDescription2)
                .transactionDescription3(transactionDescription3)
                .transactionDescription4(transactionDescription4)
                .transactionDescription5(transactionDescription5)
                .transactionDescription6(transactionDescription6)
                .amount(amount)
                .originalAmount(originalAmount)
                .reference(reference)
                .previousBalance(previousBalance)
                .currentBalance(currentBalance)
                .generateAdvice(GENERATE_ADVICE)
                .status(status)
                .build();

        List<AccountTransactionHoldEntity> accountTransactionHoldEntityList = Collections.singletonList(accountTransactionHoldEntity);
        when(urbisService.getAccountTransactionsHold(CUSTOMER_ID, request)).thenReturn(accountTransactionHoldEntityList);
        when(accountTransactionHoldMapper.map(accountTransactionHoldEntity)).thenReturn(accountTransactionHold);
        TransactionHoldResponse result = subject.hold(request);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void creditCardTransactions_returnSuccess() {
        CreditCardTransactionsRequest request = CreditCardTransactionsRequest.builder().build();
        when(creditCardService.postCreditCardsTransactions(request)).thenReturn(CreditCardTransactionsResponse.builder().data(CreditCardTransactionsData.builder().transactions(Collections.emptyList()).build()).build());
        CreditCardTransactionsResponse result = subject.creditCardTransactions(request);
        verify(merchantService).mapMerchantCategoryForCreditTransactions(anyList(), any());
    }
    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountCompletedTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsDataWithDispute() {
        String target = "debit";
        String id = "XXXX";
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        String transactionDescription6 = "XXXX";
        BigDecimal amount = BigDecimal.valueOf(300.6834);
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("completed")
                .id(ID)
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .debitCreditIndicator("debit")
                .build();
        DisputeCaseObject disputeCaseObject = DisputeCaseObject.builder()
                .accountId("XXXX")
                .build();
        List<DisputeCaseObject> disputeCaseObjectList = new ArrayList<>();
        disputeCaseObjectList.add(disputeCaseObject);
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = ListOfDisputeCasesDisput.builder()
                .listOfDisputeCasesDisput(disputeCaseObjectList)
                .build();

        Dispute dispute = Dispute.builder()
                .isDisputed(true)
                .crmCaseId("XXXX")
                .reportDateTime("XXXX")
                .build();
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .id("XXXX")
                .originalAmount(positiveAmount)
                .amount(positiveAmount)
                .dispute(dispute)
                .build();
        List<AccountTransaction> accountTransactionList = new ArrayList<>();
        accountTransactionList.add(accountTransaction);
        AccountTransactionEntity accountTransactionEntity = AccountTransactionEntity.builder()
                .id(id)
                .transactionDescription6(transactionDescription6)
                .amount(amount)
                .build();
        List<AccountTransactionEntity> accountTransactionEntitieslist = new ArrayList<>();
        accountTransactionEntitieslist.add(accountTransactionEntity);
        Map<String, DisputeCaseObject> disputeCaseObjectMap = new HashMap<>();
        disputeCaseObjectMap.put(disputeCaseObject.getAccountId(), disputeCaseObject);
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransactions accountTransactions = AccountTransactions.builder()
                .transactions(accountTransactionList)
                .build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder()
                .data(accountTransactions)
                .build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
//        when(disputeTransactionService.getDisputeDetail(target)).thenReturn(disputeCaseResponse);
        when(urbisService.getAccountTransactions(getContext().getCustomerId(), request)).thenReturn(accountTransactionEntitieslist);
        when(accountTransactionsMapper.map(accountTransactionEntity)).thenReturn(accountTransaction);
//        when(disputeTransactionMapper.map(true, accountTransaction, disputeCaseObjectMap)).thenReturn(dispute);
        when(disputeService.filterDebitDisputeTransaction(accountTransactionList,target)).thenReturn(accountTransactionList);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
    }

    @Test
    @DisplayName("Get accounts failed_pending transactions test")
    void whenAccountCompletedTransactionsRequestDataIsPassedTogetAccountTransactionsExpectSubjectToReturnTheTransactionsDataWithCreditDispute() {
        String target = "credit";
        String id = "XXXX";
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        String transactionDescription6 = "XXXX";
        BigDecimal amount = BigDecimal.valueOf(300.6834);
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .status("completed")
                .id(ID)
                .pageSize(1L)
                .offset(1L)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .debitCreditIndicator(target)
                .build();
        DisputeCaseObject disputeCaseObject = DisputeCaseObject.builder()
                .accountId("XXXX")
                .build();
        List<DisputeCaseObject> disputeCaseObjectList = new ArrayList<>();
        disputeCaseObjectList.add(disputeCaseObject);
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = ListOfDisputeCasesDisput.builder()
                .listOfDisputeCasesDisput(disputeCaseObjectList)
                .build();

        Dispute dispute = Dispute.builder()
                .isDisputed(true)
                .crmCaseId("XXXX")
                .reportDateTime("XXXX")
                .build();
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .id("XXXX")
                .originalAmount(positiveAmount)
                .amount(positiveAmount)
                .dispute(dispute)
                .build();
        List<AccountTransaction> accountTransactionList = new ArrayList<>();
        accountTransactionList.add(accountTransaction);
        AccountTransactionEntity accountTransactionEntity = AccountTransactionEntity.builder()
                .id(id)
                .transactionDescription6(transactionDescription6)
                .amount(amount)
                .build();
        List<AccountTransactionEntity> accountTransactionEntitieslist = new ArrayList<>();
        accountTransactionEntitieslist.add(accountTransactionEntity);
        Map<String, DisputeCaseObject> disputeCaseObjectMap = new HashMap<>();
        disputeCaseObjectMap.put(disputeCaseObject.getAccountId(), disputeCaseObject);
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder().build();
        Map<String, TmsxUrbisOperationTypesEntity> tmsxConfigurations = new HashMap<>();
        tmsxConfigurations.put(OPERTATION_TYPE, tmsxUrbisOperationTypesEntity);
        AccountTransactions accountTransactions = AccountTransactions.builder()
                .transactions(accountTransactionList)
                .build();
        AccountTransactionsResponse expected = AccountTransactionsResponse.builder()
                .data(accountTransactions)
                .build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        when(accountTransactionsResponseMapper.map(any(List.class))).thenReturn(expected);
        when(urbisService.getAccountTransactions(getContext().getCustomerId(), request)).thenReturn(accountTransactionEntitieslist);
        when(accountTransactionsMapper.map(accountTransactionEntity)).thenReturn(accountTransaction);
        when(disputeService.filterCreditDisputeTransaction(accountTransactionList,target)).thenReturn(accountTransactionList);
        AccountTransactionsResponse result = subject.getAccountTransactions(request);
        assertThat(result).isEqualTo(expected);
        verify(accountTransactionsResponseMapper).map(any(List.class));
    }
}
