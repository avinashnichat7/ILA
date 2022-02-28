package com.neo.v1.client;

import com.neo.v1.model.catalogue.CategoryListResponse;
import com.neo.v1.model.catalogue.MerchantCodeListResponse;
import com.neo.v1.model.catalogue.MerchantListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "ProductCatalogueClient", url = "${te.product.service.base.url}")
public interface ProductCatalogueClient {

    @GetMapping(path = "/merchant/category", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    CategoryListResponse getProductCatalogueMerchantCategory(@RequestHeader String customerId,
                                                             @RequestHeader String userId,
                                                             @RequestParam String language,
                                                             @RequestParam String unit);

    @GetMapping(path = "/merchant", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    MerchantListResponse getProductCatalogueMerchant(@RequestHeader String customerId,
                                                     @RequestHeader String userId,
                                                     @RequestParam String language,
                                                     @RequestParam String unit);

    @GetMapping(path = "/merchant/code", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    MerchantCodeListResponse getProductCatalogueMerchantCode(@RequestHeader String customerId,
                                                             @RequestHeader String userId,
                                                             @RequestParam String language,
                                                             @RequestParam String unit);

}