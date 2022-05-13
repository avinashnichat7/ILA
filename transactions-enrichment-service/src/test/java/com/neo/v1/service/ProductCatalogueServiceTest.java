package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.client.ProductCatalogueClient;
import com.neo.v1.product.catalogue.model.*;
import feign.RetryableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCatalogueServiceTest {

    private static final String LANGUAGE = "en";
    private static final String UNIT = "neo";
    private static final String CUSTOMER_ID = "C1234";
    private static final String USER_ID = "U1234";

    @InjectMocks
    private ProductCatalogueService subject;

    @Mock
    private ProductCatalogueClient productCatalogueClient;

    @BeforeEach
    void before() {
        GenericRestParamDto genericRestParamDto = GenericRestParamDto.builder()
                .locale(new Locale(LANGUAGE))
                .unit(UNIT)
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .build();
        GenericRestParamContextHolder.setContext(genericRestParamDto);
    }

    @AfterEach
    void after() {
        GenericRestParamContextHolder.clearContext();
    }

    @Test
    void getProductCatalogueMerchantCategory_throwException() {
        when(productCatalogueClient.getProductCatalogueMerchantCategory(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenThrow(RetryableException.class);

        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getProductCatalogueMerchantCategory()).getKeyMapping();
        assertEquals(PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE, keyMapping);

        verify(productCatalogueClient).getProductCatalogueMerchantCategory(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getProductCatalogueMerchantCategory_returnCategoryDetailList() {
        List<CategoryDetail> expected = Collections.singletonList(CategoryDetail.builder().build());

        when(productCatalogueClient.getProductCatalogueMerchantCategory(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenReturn(CategoryListResponse.builder()
                .data(CategoryListData.builder().merchantCategories(Collections.singletonList(CategoryDetail.builder().build())).build()).build());

        List<CategoryDetail> result = subject.getProductCatalogueMerchantCategory();
        assertThat(result.get(0)).isEqualToComparingFieldByField(expected.get(0));

        verify(productCatalogueClient).getProductCatalogueMerchantCategory(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getProductCatalogueMerchant_returnMerchantDetailList(){
        List<MerchantDetail> expected = Collections.singletonList(MerchantDetail.builder().build());
        when(productCatalogueClient.getProductCatalogueMerchant(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenReturn(MerchantListResponse.builder().data(MerchantListData.builder()
                .merchants(Collections.singletonList(MerchantDetail.builder().build())).build()).build());
        List<MerchantDetail> result = subject.getProductCatalogueMerchant();
        assertThat(result.get(0)).isEqualToComparingFieldByField(expected.get(0));
        verify(productCatalogueClient).getProductCatalogueMerchant(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getProductCatalogueMerchant_throwException() {
        when(productCatalogueClient.getProductCatalogueMerchant(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getProductCatalogueMerchant()).getKeyMapping();
        assertEquals(PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE, keyMapping);
        verify(productCatalogueClient).getProductCatalogueMerchant(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getProductCatalogueMerchantCode_returnMerchantCodeDetailList(){
        List<MerchantCodeDetail> expected = Collections.singletonList(MerchantCodeDetail.builder().build());
        when(productCatalogueClient.getProductCatalogueMerchantCode(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenReturn(MerchantCodeListResponse.builder().data(MerchantCodeListData.builder()
                .merchantCodes(Collections.singletonList(MerchantCodeDetail.builder().build())).build()).build());
        List<MerchantCodeDetail> result = subject.getProductCatalogueMerchantCode();
        assertThat(result.get(0)).isEqualToComparingFieldByField(expected.get(0));
        verify(productCatalogueClient).getProductCatalogueMerchantCode(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getProductCatalogueMerchantCode_throwException() {
        when(productCatalogueClient.getProductCatalogueMerchantCode(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenThrow(RetryableException.class);
        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getProductCatalogueMerchantCode()).getKeyMapping();
        assertEquals(PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE, keyMapping);
        verify(productCatalogueClient).getProductCatalogueMerchantCode(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

}
