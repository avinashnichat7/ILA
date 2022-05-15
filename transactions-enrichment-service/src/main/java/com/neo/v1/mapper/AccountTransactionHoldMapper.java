package com.neo.v1.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionHoldEntity;
import com.neo.v1.service.CharityService;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionHold;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AccountTransactionHoldMapper {
	
	private static final Integer GENERATE_ADVICE = 1;
	private final CurrencyMapper currencyMapper;
	
	public AccountTransactionHold map(AccountTransactionHoldEntity entity) {
        return AccountTransactionHold.builder()
                .id(entity.getId())
                .holdDate(entity.getTransactionDate())
                .holdExpiryDate(entity.getHoldExpiryDate())
                .holdType(entity.getTransactionType())
                .holdCurrency(currencyMapper.map(entity.getTransactionCurrency(), entity.getTransactionCurrencyPlaces()))
                .holdAmount(entity.getAmount().setScale(Integer.parseInt(entity.getAccountCurrencyPlaces()), BigDecimal.ROUND_HALF_UP))
                .holdReferenceNumber(entity.getReference())
                .previousBalance(entity.getPreviousBalance())
                .currentBalance(entity.getCurrentBalance())
                .generateAdvice(GENERATE_ADVICE.equals(entity.getGenerateAdvice()))
                .holdDescription1(entity.getTransactionDescription1())
                .holdDescription2(entity.getTransactionDescription2())
                .holdDescription3(entity.getTransactionDescription3())
                .holdDescription4(entity.getTransactionDescription4())
                .holdDescription5(entity.getTransactionDescription5())
                .holdDescription6(entity.getTransactionDescription6())
                .build();
    }

}
