package com.neo.v1.mapper;

import com.neo.v1.model.PaymentEntry;
import com.neo.v1.model.TmsxCreateHoldRequest;
import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.tmsx.Amount;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import org.springframework.stereotype.Component;

import static com.neo.v1.enums.TransferType.FAWRI;

@Component
public class TransferFeesRequestMapper {

    public TransferFeesRequest map(TmsxCreateHoldRequest tmsxCreateHoldRequest, String transactionType) {
       PaymentEntry paymentEntry = tmsxCreateHoldRequest.getPaymentEntry();
        Amount amount = paymentEntry.getAmount();
        return TransferFeesRequest.builder()
                .sourceAccountId(paymentEntry.getDebitAccount())
                .destinationAccountId(paymentEntry.getCreditAccount())
                .amount(amount.getValue())
                .currency(amount.getCurrency())
                .transactionType(transactionType)
                .build();
    }

    public TransferFeesRequest mapForPendingFawri(String sourceAccountId, AccountTransaction accountTransaction) {
        return TransferFeesRequest.builder()
                .sourceAccountId(sourceAccountId)
                .amount(accountTransaction.getAmount().abs())
                .currency(accountTransaction.getAccountCurrency().getCode())
                .transactionType(FAWRI.getValue())
                .build();
    }
}