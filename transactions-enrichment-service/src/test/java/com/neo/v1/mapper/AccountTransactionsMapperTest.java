package com.neo.v1.mapper;

import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.entity.TransferOperationTypeEntity;
import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.model.PaymentDetails;
import com.neo.v1.model.account.TransferCharge;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.service.CharityService;
import com.neo.v1.tmsx.Amount;
import com.neo.v1.tmsx.PaymentIdentification;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.neo.v1.constants.TransactionEnrichmentConstants.FAWRI_CHARGES_DESCRIPTION_1;
import static com.neo.v1.constants.TransactionEnrichmentConstants.FAWRI_VAT_DESCRIPTION_1;
import static com.neo.v1.constants.TransactionEnrichmentConstants.NARRATIVE_SIZE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWATEER;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransactionsMapperTest {

    private static final Integer GENERATE_ADVICE = 1;
    private static String TRANSACTION_ID = "Trans-1234";
    private static String END_TO_END_ID = "EmdId-1234";
    private static BigDecimal VALUE_ONE = ONE;
    private static String OPERATION_TYPE = "EFT-NRT";
    private static String LOCAL_STATUS = "CREATED";
    @InjectMocks
    private AccountTransactionsMapper subject;
    @Mock
    private CurrencyMapper currencyMapper;
    @Mock
    private CharityService charityService;

    @Test
    public void map_isCalledWithAccountTransactionEntity_ReturnAccountTransaction() {
        String transactionCurrencyCode = "XXXX";
        String transactionCurrencyPlaces = "XXXX";
        String accountCurrencyCode = "XXXX";
        String accountCurrencyPlaces = "XXXX";
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
        String mcCode = "123";
        String merchantName = "merchantName";

        Currency transactionCurrency = Currency.builder()
                .code(transactionCurrencyCode)
                .decimalPlaces(transactionCurrencyPlaces)
                .build();

        Currency accountCurrency = Currency.builder()
                .code(accountCurrencyCode)
                .decimalPlaces(accountCurrencyPlaces)
                .build();
        when(currencyMapper.map(transactionCurrencyCode, transactionCurrencyPlaces)).thenReturn(transactionCurrency).thenReturn(accountCurrency);
        AccountTransaction expected = AccountTransaction.builder()
                .id(id)
                .transactionDate(transactionDate)
                .valueDate(valueDate)
                .transactionType(transactionType)
                .transactionCurrency(transactionCurrency)
                .transactionExchangeRate(transactionExchangeRate)
                .accountCurrency(accountCurrency)
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
                .generateAdvice(GENERATE_ADVICE.equals(generateAdvice))
                .status(status)
                .merchantName(merchantName)
                .mcCode(mcCode)
                .build();

        AccountTransactionEntity entity = AccountTransactionEntity.builder()
                .id(id)
                .transactionDate(transactionDate)
                .valueDate(valueDate)
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
                .mcCode(mcCode)
                .merchantName(merchantName)
                .build();

        AccountTransaction result = subject.map(entity);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);


        verify(currencyMapper, times(2)).map(transactionCurrencyCode, transactionCurrencyPlaces);
    }

    @Test
    public void map_IsCalledWithAccountPendingTransactionEntity_ThenAccountTransactionToBeReturned() {
        String transactionCurrencyCode = "XXXX";
        String transactionCurrencyPlaces = "XXXX";
        String accountCurrencyCode = "XXXX";
        String accountCurrencyPlaces = "XXXX";
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
        String status = "pending";
        String mcCode = "123";
        String merchantName = "merchantName";

        Currency transactionCurrency = Currency.builder()
                .code(transactionCurrencyCode)
                .decimalPlaces(transactionCurrencyPlaces)
                .build();

        Currency accountCurrency = Currency.builder()
                .code(accountCurrencyCode)
                .decimalPlaces(accountCurrencyPlaces)
                .build();
        when(currencyMapper.map(transactionCurrencyCode, transactionCurrencyPlaces)).thenReturn(transactionCurrency).thenReturn(accountCurrency);
        AccountTransaction expected = AccountTransaction.builder()
                .id(id)
                .transactionDate(transactionDate)
                .valueDate(valueDate)
                .transactionType(transactionType)
                .transactionCurrency(transactionCurrency)
                .transactionExchangeRate(transactionExchangeRate)
                .accountCurrency(accountCurrency)
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
                .generateAdvice(GENERATE_ADVICE.equals(generateAdvice))
                .status(status)
                .mcCode(mcCode)
                .merchantName(merchantName)
                .build();

        AccountPendingTransactionEntity entity = AccountPendingTransactionEntity.builder()
                .id(id)
                .transactionDate(transactionDate)
                .valueDate(valueDate)
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
                .mcCode(mcCode)
                .merchantName(merchantName)
                .build();

        AccountTransaction result = subject.map(entity);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(currencyMapper, times(2)).map(transactionCurrencyCode, transactionCurrencyPlaces);
    }

    @Test
    void map_IsCalledWithListOFAccountTransaction_ExpectSubjectToReturnAccountTransactions() {
        AccountTransaction accountTransaction = AccountTransaction.builder().build();
        List<AccountTransaction> accountTransactionList = Stream.of(accountTransaction).collect(Collectors.toList());

        AccountTransactions expected = AccountTransactions.builder()
                .transactions(accountTransactionList)
                .build();
        AccountTransactions result = subject.map(accountTransactionList);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void map_IsCalledWithPaymentDetailsAndCurrencyAndLocalStatusAndTmsxUrbisOperationTypesEntity_ExpectSubjectToReturnAccountTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime valueDateTime = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        PaymentIdentification paymentIdentification = PaymentIdentification.builder()
                .transactionId(TRANSACTION_ID)
                .endToEndId(END_TO_END_ID)
                .build();
        Amount instructedAmount = Amount.builder().value(VALUE_ONE).currency("BHD").build();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentIdentification(paymentIdentification)
                .instructedAmount(instructedAmount)
                .valueDate(valueDate)
                .operationType(OPERATION_TYPE)
                .localStatus(LOCAL_STATUS)
                .build();
        Currency accountCurrency = Currency.builder().build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWATEER).build()).build();
        List<String> narrativeLines = Stream.of("Line1", "Line2", "Line3", "Line4", "Line5", "Line6").collect(Collectors.toList());
        Currency transactionCurrency = Currency.builder()
                .code(instructedAmount.getCurrency())
                .decimalPlaces("1")
                .build();
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        String[] narratives = narrativeLines.toArray(new String[NARRATIVE_SIZE]);

        AccountTransaction expected = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(valueDateTime)
                .transactionType(OPERATION_TYPE)
                .transactionCurrency(transactionCurrency)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .transactionDescription1(narratives[0])
                .transactionDescription2(narratives[1])
                .transactionDescription3(narratives[2])
                .transactionDescription4(narratives[3])
                .transactionDescription5(narratives[4])
                .transactionDescription6(narratives[5])
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .status(LOCAL_STATUS)
                .build();

        AccountTransaction result = subject.map(paymentDetails, accountCurrency, tmsxUrbisOperationTypesEntity, codeDecimalPlacesMap);
        assertThat(result).isNotNull();
    }

    @Test
    void map_IsCalledWithPaymentDetailsAndCurrencyAndLocalStatusAndTmsxUrbisOperationTypesEntityWithNullNarratives_ExpectSubjectToReturnAccountTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        String status = "CREATED";
        Map<String, Integer> codeDecimalPlacesMap = new HashMap<>();
        codeDecimalPlacesMap.put("BHD", 1);
        PaymentIdentification paymentIdentification = PaymentIdentification.builder()
                .transactionId(TRANSACTION_ID)
                .endToEndId(END_TO_END_ID)
                .build();
        Amount instructedAmount = Amount.builder().value(VALUE_ONE).currency("BHD").build();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentIdentification(paymentIdentification)
                .instructedAmount(instructedAmount)
                .valueDate(valueDate)
                .operationType(OPERATION_TYPE)
                .status(status)
                .creationDate(creationDate.atZone(ZoneId.systemDefault()))
                .build();
        Currency accountCurrency = Currency.builder().build();
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWATEER).build()).build();
        Currency transactionCurrency = Currency.builder()
                .code(instructedAmount.getCurrency())
                .decimalPlaces("1")
                .build();
        when(currencyMapper.map(instructedAmount.getCurrency(), "1")).thenReturn(transactionCurrency);
        AccountTransaction expected = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionCurrency(transactionCurrency)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .status(status)
                .build();

        AccountTransaction result = subject.map(paymentDetails, accountCurrency, tmsxUrbisOperationTypesEntity, codeDecimalPlacesMap);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

    }

    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndChargeAndVat_ExpectSubjectToReturnAccountTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder().build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();

        AccountTransaction expectedCharge = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(chargeAmountInAccountCurrency.negate())
                .originalAmount(chargeAmountInAccountCurrency.negate())
                .reference(null)
                .transactionDescription1(FAWRI_CHARGES_DESCRIPTION_1)
                .build();

        AccountTransaction expectedVat = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(vatAmountInAccountCurrency)
                .originalAmount(vatAmountInAccountCurrency)
                .reference(null)
                .transactionDescription1(FAWRI_VAT_DESCRIPTION_1)
                .build();

        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expectedCharge);
        assertThat(result.get(1)).isEqualToComparingFieldByField(expectedVat);

    }


    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndFilter_returnChargeTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder()
                .filter("charge")
                .build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();

        AccountTransaction expectedCharge = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(chargeAmountInAccountCurrency.negate())
                .originalAmount(chargeAmountInAccountCurrency.negate())
                .reference(null)
                .transactionDescription1(FAWRI_CHARGES_DESCRIPTION_1)
                .build();


        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expectedCharge);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndFilter_returnVatTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder()
                .filter("vat")
                .build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();


        AccountTransaction expectedVat = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(vatAmountInAccountCurrency)
                .originalAmount(vatAmountInAccountCurrency)
                .reference(null)
                .transactionDescription1(FAWRI_VAT_DESCRIPTION_1)
                .build();


        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expectedVat);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndFilterByAmount_returnVatTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder()
                .fromAmount(BigDecimal.valueOf(0.001)).toAmount(BigDecimal.valueOf(0.09))
                .build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();


        AccountTransaction expectedVat = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(vatAmountInAccountCurrency)
                .originalAmount(vatAmountInAccountCurrency)
                .reference(null)
                .transactionDescription1(FAWRI_VAT_DESCRIPTION_1)
                .build();


        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expectedVat);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndFilterByAmount_returnChargeTransaction() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder()
                .fromAmount(BigDecimal.valueOf(0.1)).toAmount(BigDecimal.valueOf(10))
                .build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();


        AccountTransaction expectedCharge = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(chargeAmountInAccountCurrency.negate())
                .originalAmount(chargeAmountInAccountCurrency.negate())
                .reference(null)
                .transactionDescription1(FAWRI_CHARGES_DESCRIPTION_1)
                .build();

        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.get(0)).isEqualToComparingFieldByField(expectedCharge);
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    void mapFawriChargesAndVat_withFawriTransactionsAndFilterByAmount_returnEmptyTransactionList() {
        LocalDate valueDate = LocalDate.now();
        LocalDateTime creationDate = LocalDateTime.of(valueDate, LocalTime.of(0, 0));
        BigDecimal chargeAmountInAccountCurrency = BigDecimal.valueOf(0.1);
        BigDecimal vatAmountInAccountCurrency = BigDecimal.valueOf(-0.005);
        Currency accountCurrency = Currency.builder().build();
        AccountTransactionsRequest accountTransactionsRequest = AccountTransactionsRequest.builder()
                .fromAmount(BigDecimal.valueOf(1)).toAmount(BigDecimal.valueOf(10))
                .build();
        TransferCharge transferCharge = TransferCharge.builder()
                .chargeAmountInAccountCurrency(chargeAmountInAccountCurrency)
                .vatAmountInAccountCurrency(vatAmountInAccountCurrency)
                .build();
        AccountTransaction fawriTransaction = AccountTransaction.builder()
                .id(END_TO_END_ID)
                .valueDate(valueDate)
                .transactionDate(creationDate)
                .transactionType(OPERATION_TYPE)
                .transactionExchangeRate(ONE)
                .accountCurrency(accountCurrency)
                .amount(VALUE_ONE)
                .originalAmount(VALUE_ONE)
                .reference(END_TO_END_ID)
                .build();

        List<AccountTransaction> result = subject.mapFawriChargesAndVat(Collections.singletonList(fawriTransaction), transferCharge, accountTransactionsRequest);
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void getCharityDetails_withPaymentDetails_returnCharityItemData() {
        PaymentDetails paymentDetails = PaymentDetails.builder().remittanceInformation("1/2").build();
        CharityItemData expected = CharityItemData.builder().build();
        when(charityService.getCharityDetail(anyLong(), anyLong())).thenReturn(expected);
        CharityItemData result = subject.getCharityDetails(paymentDetails);
        assertThat(result).isEqualToComparingFieldByField(expected);

    }
}