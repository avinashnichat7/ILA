package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;

@Component
public class CustomerMerchantCategoryEntityMapper {

    public CustomerMerchantCategoryEntity map(AccountTransaction accountTransaction, String customerCategoryId, boolean isCustom) {
        return CustomerMerchantCategoryEntity.builder()
                .customerId(getContext().getCustomerId())
                .name(StringUtils.isBlank(accountTransaction.getMerchantName()) ? accountTransaction.getTransactionType() : accountTransaction.getMerchantName())
                .categoryId(customerCategoryId)
                .isCustom(isCustom)
                .active(Boolean.TRUE)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
