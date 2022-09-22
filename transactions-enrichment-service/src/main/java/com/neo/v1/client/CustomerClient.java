package com.neo.v1.client;

import com.neo.v1.model.customer.CustomerDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.neo.core.constants.GenericRestParamConstants.CUSTOMER_ID;
import static com.neo.core.constants.GenericRestParamConstants.LANGUAGE;
import static com.neo.core.constants.GenericRestParamConstants.UNIT;
import static com.neo.core.constants.GenericRestParamConstants.USER_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "CustomerFeignService", url = "${te.customer.service.base.url}")
public interface CustomerClient {

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    CustomerDetailResponse getCustomerDetail(@RequestHeader(name = CUSTOMER_ID) String customerId,
                                             @RequestHeader(name = USER_ID) String userId,
                                             @RequestParam(name = LANGUAGE) String language,
                                             @RequestParam(name = UNIT) String unit);
}