package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;

@Component
public class CustomerMerchantCategoryEntityMapper {

    public CustomerMerchantCategoryEntity map(AccountTransaction accountTransaction, CustomerCategoryEntity customerCategoryEntity) {
        return CustomerMerchantCategoryEntity.builder()
                .customerId(getContext().getCustomerId())
                .name(accountTransaction.getMerchantName())
                .customerCategory(customerCategoryEntity)
                .isCustom(Boolean.TRUE)
                .active(Boolean.TRUE)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
