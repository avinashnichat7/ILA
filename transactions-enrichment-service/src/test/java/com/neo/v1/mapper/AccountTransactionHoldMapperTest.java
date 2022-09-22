package com.neo.v1.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neo.v1.entity.urbis.AccountTransactionHoldEntity;
import com.neo.v1.transactions.enrichment.model.AccountTransactionHold;
import com.neo.v1.transactions.enrichment.model.Currency;

@ExtendWith(MockitoExtension.class)
class AccountTransactionHoldMapperTest {
	
	private static final Integer GENERATE_ADVICE = 1;
	@InjectMocks
    private AccountTransactionHoldMapper subject;
    @Mock
    private CurrencyMapper currencyMapper;

    @Test
    void map_isCalledWithAccountTransactionEntity_ReturnAccountTransaction() {
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

       Currency transactionCurrency = Currency.builder()
               .code(transactionCurrencyCode)
               .decimalPlaces(transactionCurrencyPlaces)
               .build();

       Currency accountCurrency = Currency.builder()
               .code(accountCurrencyCode)
               .decimalPlaces(accountCurrencyPlaces)
               .build();
       when(currencyMapper.map(transactionCurrencyCode, transactionCurrencyPlaces)).thenReturn(transactionCurrency).thenReturn(accountCurrency);
       AccountTransactionHold expected = AccountTransactionHold.builder()
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

       AccountTransactionHoldEntity entity = AccountTransactionHoldEntity.builder()
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

       AccountTransactionHold result = subject.map(entity);

       assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);


       verify(currencyMapper, times(1)).map(transactionCurrencyCode, transactionCurrencyPlaces);
   }

}
