package com.neo.v1.mapper;

import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.entity.a24.A24AccountTransactionEntity;
import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionDetailEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.enums.TransactionEntryType;
import com.neo.v1.model.PaymentDetails;
import com.neo.v1.model.account.TransferCharge;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.service.CharityService;
import com.neo.v1.model.tmsx.Amount;
import com.neo.v1.model.tmsx.PaymentIdentification;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.Currency;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.neo.v1.constants.TransactionEnrichmentConstants.BACKSLASH;
import static com.neo.v1.constants.TransactionEnrichmentConstants.FAWRI_CHARGES_DESCRIPTION_1;
import static com.neo.v1.constants.TransactionEnrichmentConstants.FAWRI_VAT_DESCRIPTION_1;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_ACCOUNT_TRANSACTION_EXCHANGE_RATE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER_CODE;
import static com.neo.v1.entity.PaymentEntryType.URBIS;
import static com.neo.v1.util.TmsxUtil.getNarrativeLines;
import static java.lang.String.valueOf;

@Component
@AllArgsConstructor
public class AccountTransactionsMapper {

    private static final Integer GENERATE_ADVICE = 1;
    private final CurrencyMapper currencyMapper;
    private final CharityService charityService;

    public AccountTransaction map(AccountTransactionEntity entity) {
        return AccountTransaction.builder()
                .id(entity.getId())
                .transactionDate(entity.getTransactionDate())
                .valueDate(entity.getValueDate())
                .transactionType(entity.getTransactionType())
                .transactionCurrency(currencyMapper.map(entity.getTransactionCurrency(), entity.getTransactionCurrencyPlaces()))
                .transactionExchangeRate(entity.getTransactionExchangeRate())
                .accountCurrency(currencyMapper.map(entity.getAccountCurrency(), entity.getAccountCurrencyPlaces()))
                .transactionDescription1(entity.getTransactionDescription1())
                .transactionDescription2(entity.getTransactionDescription2())
                .transactionDescription3(entity.getTransactionDescription3())
                .transactionDescription4(entity.getTransactionDescription4())
                .transactionDescription5(entity.getTransactionDescription5())
                .transactionDescription6(entity.getTransactionDescription6())
                .amount(entity.getAmount())
                .originalAmount(entity.getOriginalAmount())
                .reference(entity.getReference())
                .previousBalance(entity.getPreviousBalance())
                .currentBalance(entity.getCurrentBalance())
                .generateAdvice(GENERATE_ADVICE.equals(entity.getGenerateAdvice()))
                .status(entity.getStatus())
//                .category(AccountTransactionCategory.builder().build())
                .mcCode(entity.getMcCode())
                .merchantName(entity.getMerchantName())
                .build();
    }
    public AccountTransaction map(A24AccountTransactionEntity entity) {
        return AccountTransaction.builder()
                .id(entity.getId())
                .transactionDate(entity.getTransactionDate())
                .valueDate(entity.getValueDate())
                .transactionType(entity.getTransactionType())
                .transactionCurrency(currencyMapper.map(entity.getTransactionCurrency(), entity.getTransactionCurrencyPlaces()))
                .transactionExchangeRate(entity.getTransactionExchangeRate())
                .accountCurrency(currencyMapper.map(entity.getAccountCurrency(), entity.getAccountCurrencyPlaces()))
                .transactionDescription1(entity.getTransactionDescription1())
                .transactionDescription2(entity.getTransactionDescription2())
                .transactionDescription3(entity.getTransactionDescription3())
                .transactionDescription4(entity.getTransactionDescription4())
                .transactionDescription5(entity.getTransactionDescription5())
                .transactionDescription6(entity.getTransactionDescription6())
                .amount(entity.getAmount())
                .originalAmount(entity.getOriginalAmount())
                .reference(entity.getReference())
                .generateAdvice(GENERATE_ADVICE.equals(entity.getGenerateAdvice()))
                .status(entity.getStatus())
                .build();
    }

    public AccountTransaction map(AccountTransaction object) {
        return AccountTransaction.builder()
                .id(object.getId())
                .transactionDate(object.getTransactionDate())
                .valueDate(object.getValueDate())
                .transactionType(object.getTransactionType())
                .transactionCurrency(object.getTransactionCurrency())
                .transactionExchangeRate(object.getTransactionExchangeRate())
                .accountCurrency(object.getAccountCurrency())
                .transactionDescription1(object.getTransactionDescription1())
                .transactionDescription2(object.getTransactionDescription2())
                .transactionDescription3(object.getTransactionDescription3())
                .transactionDescription4(object.getTransactionDescription4())
                .transactionDescription5(object.getTransactionDescription5())
                .transactionDescription6(object.getTransactionDescription6())
                .amount(object.getAmount())
                .originalAmount(object.getOriginalAmount())
                .reference(object.getReference())
                .previousBalance(object.getPreviousBalance())
                .currentBalance(object.getCurrentBalance())
                .generateAdvice(object.isGenerateAdvice())
                .status(object.getStatus())
                .mcCode(object.getMcCode())
                .merchantName(object.getMerchantName())
                .build();
    }


    public AccountTransaction map(AccountPendingTransactionEntity entity) {
        return AccountTransaction.builder()
                .id(entity.getId())
                .transactionDate(entity.getTransactionDate())
                .valueDate(entity.getValueDate())
                .transactionType(entity.getTransactionType())
                .transactionCurrency(currencyMapper.map(entity.getTransactionCurrency(), entity.getTransactionCurrencyPlaces()))
                .transactionExchangeRate(entity.getTransactionExchangeRate())
                .accountCurrency(currencyMapper.map(entity.getAccountCurrency(), entity.getAccountCurrencyPlaces()))
                .transactionDescription1(entity.getTransactionDescription1())
                .transactionDescription2(entity.getTransactionDescription2())
                .transactionDescription3(entity.getTransactionDescription3())
                .transactionDescription4(entity.getTransactionDescription4())
                .transactionDescription5(entity.getTransactionDescription5())
                .transactionDescription6(entity.getTransactionDescription6())
                .amount(entity.getAmount())
                .originalAmount(entity.getOriginalAmount())
                .reference(entity.getReference())
                .previousBalance(entity.getPreviousBalance())
                .currentBalance(entity.getCurrentBalance())
                .generateAdvice(GENERATE_ADVICE.equals(entity.getGenerateAdvice()))
                .status(entity.getStatus())
                .mcCode(entity.getMcCode())
                .merchantName(entity.getMerchantName())
                .build();
    }

    public AccountTransactions map(List<AccountTransaction> accountTransactions) {
        return AccountTransactions.builder()
                .transactions(accountTransactions)
                .build();
    }

    public CharityItemData getCharityDetails(PaymentDetails paymentDetails) {
        String description = paymentDetails.getRemittanceInformation();
        CharityItemData charityDetail = CharityItemData.builder().build();
        if(Objects.nonNull(paymentDetails.getRemittanceInformation()) && description.split(BACKSLASH).length == 2) {
            String[] idList = description.split(BACKSLASH);
            Long charityId = Long.parseLong(idList[0]);
            Long purposeId = Long.parseLong(idList[1]);
            charityDetail = charityService.getCharityDetail(charityId, purposeId);
        }
        return charityDetail;
    }

    public AccountTransaction map(PaymentDetails paymentDetails, Currency accountCurrency, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity, Map<String, Integer> codeDecimalPlacesMap) {
        CharityItemData charityItemData = TRANSACTION_TYPE_CHARITY_TRANSFER_CODE.equalsIgnoreCase(paymentDetails.getTransactionTypeCode()) ? getCharityDetails(paymentDetails) : null;
        String[] narratives = getNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity, false, URBIS, TransactionEntryType.CREDIT, charityItemData);
        PaymentIdentification paymentIdentification = paymentDetails.getPaymentIdentification();
        LocalDate valueDate = paymentDetails.getValueDate();
        LocalDateTime transactionDateTime = Objects.nonNull(paymentDetails.getCreationDate()) ? paymentDetails.getCreationDate().toLocalDateTime() : null;
        Amount instructedAmount = paymentDetails.getInstructedAmount();
        BigDecimal value = instructedAmount.getValue();
        return AccountTransaction.builder()
                .id(paymentIdentification.getEndToEndId())
                .transactionDate(transactionDateTime)
                .valueDate(valueDate)
                .transactionType(paymentDetails.getOperationType())
                .transactionCurrency(currencyMapper.map(instructedAmount.getCurrency(), valueOf(codeDecimalPlacesMap.get(instructedAmount.getCurrency()))))
                .transactionExchangeRate(TMSX_ACCOUNT_TRANSACTION_EXCHANGE_RATE)
                .accountCurrency(accountCurrency)
                .transactionDescription1(narratives[0])
                .transactionDescription2(narratives[1])
                .transactionDescription3(narratives[2])
                .transactionDescription4(narratives[3])
                .transactionDescription5(narratives[4])
                .transactionDescription6(narratives[5])
                .amount(value)
                .originalAmount(value)
                .reference(paymentIdentification.getEndToEndId())
                .status(paymentDetails.getStatus())
                .transactionTypeCode(paymentDetails.getTransactionTypeCode())
                .build();
    }

    public AccountTransaction map(AccountTransactionDetailEntity accountTransactionDetailEntity) {
        return AccountTransaction.builder()
                .id(accountTransactionDetailEntity.getId())
                .transactionDate(accountTransactionDetailEntity.getTransactionDate())
                .valueDate(accountTransactionDetailEntity.getValueDate())
                .transactionType(accountTransactionDetailEntity.getTransactionType())
                .transactionCurrency(currencyMapper.map(accountTransactionDetailEntity.getTransactionCurrency(), accountTransactionDetailEntity.getTransactionCurrencyPlaces()))
                .transactionExchangeRate(accountTransactionDetailEntity.getTransactionExchangeRate())
                .accountCurrency(currencyMapper.map(accountTransactionDetailEntity.getAccountCurrency(), accountTransactionDetailEntity.getAccountCurrencyPlaces()))
                .transactionDescription1(accountTransactionDetailEntity.getTransactionDescription1())
                .transactionDescription2(accountTransactionDetailEntity.getTransactionDescription2())
                .transactionDescription3(accountTransactionDetailEntity.getTransactionDescription3())
                .transactionDescription4(accountTransactionDetailEntity.getTransactionDescription4())
                .transactionDescription5(accountTransactionDetailEntity.getTransactionDescription5())
                .transactionDescription6(accountTransactionDetailEntity.getTransactionDescription6())
                .amount(accountTransactionDetailEntity.getAmount())
                .originalAmount(accountTransactionDetailEntity.getOriginalAmount())
                .reference(accountTransactionDetailEntity.getReference())
                .previousBalance(accountTransactionDetailEntity.getPreviousBalance())
                .currentBalance(accountTransactionDetailEntity.getCurrentBalance())
                .generateAdvice(GENERATE_ADVICE.equals(accountTransactionDetailEntity.getGenerateAdvice()))
                .status(accountTransactionDetailEntity.getStatus())
                .build();
    }

    public List<AccountTransaction> mapFawriChargesAndVat(List<AccountTransaction> fawriTransactions, TransferCharge charge, AccountTransactionsRequest request) {
        List<AccountTransaction> chargesAndVatTransactionList = new ArrayList<>();
        BigDecimal chargeAmount = charge.getChargeAmountInAccountCurrency().abs();
        BigDecimal vatAmount = charge.getVatAmountInAccountCurrency().abs();
        Boolean returnCharge = returnFawriChargesTransactions(chargeAmount, request.getFromAmount(), request.getToAmount(), request.getFilter());
        Boolean returnVat = returnFawriVatTransactions(vatAmount, request.getFromAmount(), request.getToAmount(), request.getFilter());
        charge.setChargeAmountInAccountCurrency(chargeAmount.negate());
        charge.setVatAmountInAccountCurrency(vatAmount.negate());

        if (returnCharge && returnVat) {
            for (AccountTransaction fawriTransaction : fawriTransactions) {
                chargesAndVatTransactionList.add(mapFawriCharge(fawriTransaction, charge));
                chargesAndVatTransactionList.add(mapFawriVat(fawriTransaction, charge));
            }
        } else if (Boolean.TRUE.equals(returnCharge)) {
            for (AccountTransaction fawriTransaction : fawriTransactions) {
                chargesAndVatTransactionList.add(mapFawriCharge(fawriTransaction, charge));
            }
        } else if (Boolean.TRUE.equals(returnVat)) {
            for (AccountTransaction fawriTransaction : fawriTransactions) {
                chargesAndVatTransactionList.add(mapFawriVat(fawriTransaction, charge));
            }
        }
        return chargesAndVatTransactionList;
    }

    private AccountTransaction mapFawriCharge(AccountTransaction fawriTransaction, TransferCharge charge) {
        AccountTransaction accountTransaction = map(fawriTransaction);
        accountTransaction.setTransactionDescription1(FAWRI_CHARGES_DESCRIPTION_1);
        accountTransaction.setTransactionDescription2(null);
        accountTransaction.setAmount(charge.getChargeAmountInAccountCurrency());
        accountTransaction.setOriginalAmount(charge.getChargeAmountInAccountCurrency());
        accountTransaction.setReference(null);
        return accountTransaction;
    }

    private AccountTransaction mapFawriVat(AccountTransaction fawriTransaction, TransferCharge charge) {
        AccountTransaction accountTransaction = map(fawriTransaction);
        accountTransaction.setTransactionDescription1(FAWRI_VAT_DESCRIPTION_1);
        accountTransaction.setTransactionDescription2(null);
        accountTransaction.setAmount(charge.getVatAmountInAccountCurrency());
        accountTransaction.setOriginalAmount(charge.getVatAmountInAccountCurrency());
        accountTransaction.setReference(null);
        return accountTransaction;
    }

    private Boolean returnFawriVatTransactions(BigDecimal vatAmount, BigDecimal fromAmount, BigDecimal toAmount, String filter) {
        return (StringUtils.isEmpty(filter) || FAWRI_VAT_DESCRIPTION_1.toUpperCase().contains(filter.toUpperCase()))
                && isAmountWithinRange(vatAmount, fromAmount, toAmount);
    }

    private Boolean returnFawriChargesTransactions(BigDecimal chargeAmount, BigDecimal fromAmount, BigDecimal toAmount, String filter) {
        return (StringUtils.isEmpty(filter) || FAWRI_CHARGES_DESCRIPTION_1.toUpperCase().contains(filter.toUpperCase()))
                && isAmountWithinRange(chargeAmount, fromAmount, toAmount);
    }

    private Boolean isAmountWithinRange(BigDecimal amountToCompare, BigDecimal fromAmount, BigDecimal toAmount) {
        return (Objects.isNull(fromAmount) || amountToCompare.compareTo(fromAmount) >= 0)
                && (Objects.isNull(toAmount) || amountToCompare.compareTo(toAmount) <= 0);
    }

}
