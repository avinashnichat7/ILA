package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.EnrichedTransactionCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MerchantCategoryMapperTest {

    @InjectMocks
    MerchantCategoryMapper subject;

    @Test
    void mapAccountTransactionCategory_withNoCustomerMerchantCategoryEntity_returnSuccess() {
        AccountTransaction expected = AccountTransaction.builder().enrichedTransactionCategory(EnrichedTransactionCategory.builder()
                .name("cname").icon("icon").color("color").iconLabelUrl("iconLabelUrl").isCustom(Boolean.TRUE).build()).build();
        AccountTransaction transaction = AccountTransaction.builder().build();
        CustomerCategoryEntity customerCategory = CustomerCategoryEntity.builder().name("cname").icon("icon").color("color").iconLabelUrl("iconLabelUrl").build();
        subject.mapCustomCategory(transaction, customerCategory);
        assertThat(transaction).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void mapAccountTransactionCategory_withMerchantDetail_returnSuccess() {
        AccountTransaction expected = AccountTransaction.builder().enrichedTransactionCategory(EnrichedTransactionCategory.builder()
                .name("cname").icon("icon").color("color").iconLabelUrl("iconLabelUrl").isCustom(false).build()).build();
        AccountTransaction transaction = AccountTransaction.builder().build();
        MerchantDetail merchantDetail = MerchantDetail.builder()
                .contentfulMerchantCategory(CategoryDetail.builder().name("cname").icon("icon").iconLabelUrl("iconLabelUrl").color("color").build()).build();
        subject.mapAccountTransactionCategory(transaction, merchantDetail);
        assertThat(transaction).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void mapAccountTransactionCategory_withNoMerchantDetail_returnSuccess() {
        AccountTransaction transaction = AccountTransaction.builder().build();
        MerchantDetail merchantDetail = MerchantDetail.builder().build();
        subject.mapAccountTransactionCategory(transaction, merchantDetail);
        assertThat(transaction).isEqualToComparingFieldByFieldRecursively(transaction);
    }

    @Test
    void mapAccountTransactionCategory_withMerchantCodeDetail_returnSuccess() {
        AccountTransaction expected = AccountTransaction.builder().enrichedTransactionCategory(EnrichedTransactionCategory.builder()
                .name("cname").icon("icon").color("color").iconLabelUrl("iconLabelUrl").isCustom(Boolean.FALSE).build()).build();
        AccountTransaction transaction = AccountTransaction.builder().build();
        MerchantCodeDetail merchantCodeDetail = MerchantCodeDetail.builder()
                .contentfulMerchantCategory(CategoryDetail.builder().name("cname").icon("icon").iconLabelUrl("iconLabelUrl").color("color").build()).build();
        subject.mapAccountTransactionCategory(transaction, merchantCodeDetail);
        assertThat(transaction).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void mapAccountTransactionCategory_withNoMerchantCodeDetail_returnSuccess() {
        AccountTransaction transaction = AccountTransaction.builder().build();
        MerchantCodeDetail merchantCodeDetail = MerchantCodeDetail.builder().build();
        subject.mapAccountTransactionCategory(transaction, merchantCodeDetail);
        assertThat(transaction).isEqualToComparingFieldByFieldRecursively(transaction);
    }
}
