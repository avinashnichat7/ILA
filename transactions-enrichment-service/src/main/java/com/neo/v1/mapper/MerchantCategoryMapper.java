package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.model.catalogue.MerchantCodeDetail;
import com.neo.v1.model.catalogue.MerchantDetail;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.EnrichedTransactionCategory;
import org.springframework.stereotype.Component;

@Component
public class MerchantCategoryMapper {

    public void mapAccountTransactionCategory(AccountTransaction transactions, CustomerAccountTransactionCategoryEntity customerAccountTransactionCategory) {
        CustomerCategoryEntity customerCategory = customerAccountTransactionCategory.getCustomerCategory();
        EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder().name(customerCategory.getName())
                .icon(customerCategory.getIcon()).iconLabelUrl(customerCategory.getIconLabelUrl()).color(customerCategory.getColor()).build();
        transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);

    }

    public void mapAccountTransactionCategory(AccountTransaction transactions, CustomerMerchantCategoryEntity customerMerchantCategoryEntity) {
        CustomerCategoryEntity customerCategory = customerMerchantCategoryEntity.getCustomerCategory();
        EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder().name(customerCategory.getName())
                .icon(customerCategory.getIcon()).iconLabelUrl(customerCategory.getIconLabelUrl()).color(customerCategory.getColor()).build();
        transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
    }

    public void mapAccountTransactionCategory(AccountTransaction transactions, MerchantDetail merchantDetail) {
        CategoryDetail merchantCategory = merchantDetail.getContentfulMerchantCategory();
        EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder().name(merchantCategory.getName())
                .icon(merchantCategory.getIcon()).iconLabelUrl(merchantCategory.getIconLabelUrl()).color(merchantCategory.getColor()).build();
        transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
    }

    public void mapAccountTransactionCategory(AccountTransaction transactions, MerchantCodeDetail merchantCodeDetail) {
        CategoryDetail merchantCategory = merchantCodeDetail.getContentfulMerchantCategory();
        EnrichedTransactionCategory enrichedTransactionCategory = EnrichedTransactionCategory.builder().name(merchantCategory.getName())
                .icon(merchantCategory.getIcon()).iconLabelUrl(merchantCategory.getIconLabelUrl()).color(merchantCategory.getColor()).build();
        transactions.setEnrichedTransactionCategory(enrichedTransactionCategory);
    }
}
