package com.neo.v1.client;

import com.neo.v1.dispute.model.DisputeCaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.neo.core.constants.GenericRestParamConstants.CUSTOMER_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "DisputeTransactionFeignService", url = "${te.dispute.service.base.url}")
public interface DisputeTransactionClient {

    @GetMapping(path = "/{target}/case", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    DisputeCaseResponse getCreditDebitDetails(@RequestHeader(name = CUSTOMER_ID) String customerId,
                                              @RequestParam(name = "target") String target);
}
