package com.neo.v1.mapper;

import com.neo.v1.model.TransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionsRequestMapper {

    public TransactionsRequest map(AccountTransactionsRequest request) {
        return TransactionsRequest.builder()
                .id(request.getId())
                .filter(request.getFilter())
                .fromAmount(request.getFromAmount())
                .toAmount(request.getToAmount())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .debitCreditIndicator(request.getDebitCreditIndicator())
                .build();
    }
}
