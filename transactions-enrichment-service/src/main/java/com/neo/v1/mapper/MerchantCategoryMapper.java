package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.EnrichedTransactionCategory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MerchantCategoryMapper {

    public void mapCustomCategory(AccountTransaction transactions, CustomerCategoryEntity customerCategory) {
        if(Objects.nonNull(customerCategory)) {
            EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder()
                    .id(customerCategory.getId().toString())
                    .name(customerCategory.getName())
                    .icon(customerCategory.getIcon())
                    .iconLabelUrl(customerCategory.getIconLabelUrl())
                    .color(customerCategory.getColor())
                    .isCustom(Boolean.TRUE)
                    .build();
            transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
        }
    }

    public void mapAccountTransactionCategory(AccountTransaction transactions, MerchantDetail merchantDetail) {
        if(Objects.nonNull(merchantDetail) && Objects.nonNull(merchantDetail.getContentfulMerchantCategory())) {
            CategoryDetail merchantCategory = merchantDetail.getContentfulMerchantCategory();
            EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder()
                    .id(merchantCategory.getId())
                    .name(merchantCategory.getName())
                    .icon(merchantCategory.getIcon())
                    .iconLabelUrl(merchantCategory.getIconLabelUrl())
                    .color(merchantCategory.getColor())
                    .isCustom(Boolean.FALSE)
                    .build();
            transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
        }
    }

    public void mapAccountTransactionCategory(AccountTransaction transactions, MerchantCodeDetail merchantCodeDetail) {
        if(Objects.nonNull(merchantCodeDetail) && Objects.nonNull(merchantCodeDetail.getContentfulMerchantCategory())) {
            CategoryDetail merchantCategory = merchantCodeDetail.getContentfulMerchantCategory();
            EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder()
                    .id(merchantCategory.getId())
                    .name(merchantCategory.getName())
                    .icon(merchantCategory.getIcon())
                    .iconLabelUrl(merchantCategory.getIconLabelUrl())
                    .color(merchantCategory.getColor())
                    .isCustom(Boolean.FALSE)
                    .build();
            transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
        }
    }
}
