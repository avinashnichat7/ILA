package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.ProductCatalogueClient;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.model.catalogue.CategoryListData;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE;

@Slf4j
@Service
@AllArgsConstructor
public class ProductCatalogueService {

    private final ProductCatalogueClient productCatalogueClient;

    List<CategoryDetail> getProductCatalogueMerchantCategory() {
        CategoryListData data;
        try {
            data = productCatalogueClient.getProductCatalogueMerchantCategory(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getLocale().getLanguage(),
                    getContext().getUnit()).getData();
        } catch (RetryableException retryableException) {
            throw new ServiceException(PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE, retryableException);
        }
        return Optional.ofNullable(data).map(CategoryListData::getMerchantCategories).orElse(Collections.emptyList());
    }
}
