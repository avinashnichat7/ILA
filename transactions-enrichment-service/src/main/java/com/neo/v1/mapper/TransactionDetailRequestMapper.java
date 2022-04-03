package com.neo.v1.mapper;

import com.neo.v1.model.transactions.TransactionDetailRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionDetailRequestMapper {

    public TransactionDetailRequest map(String accountId, String transactionReference) {
        return TransactionDetailRequest.builder()
                .accountId(accountId)
                .transactionReference(transactionReference)
                .build();
    }
}
