package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.TransactionLinkRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;

@Component
public class CustomerAccountTransactionCategoryEntityMapper {

    public CustomerAccountTransactionCategoryEntity map(AccountTransaction accountTransaction, String categoryId, TransactionLinkRequest request, boolean isCustom) {
        return CustomerAccountTransactionCategoryEntity.builder()
                .customerId(getContext().getCustomerId())
                .transactionReference(accountTransaction.getReference())
                .accountId(request.getIban())
                .transactionDate(accountTransaction.getTransactionDate())
                .categoryId(categoryId)
                .isCustom(isCustom)
                .active(Boolean.TRUE)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
