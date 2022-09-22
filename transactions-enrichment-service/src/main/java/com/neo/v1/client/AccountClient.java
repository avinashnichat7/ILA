package com.neo.v1.client;

import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.model.account.TransferFeesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "AccountService", url = "${te.account.service.base.url}")
public interface AccountClient {

    @PostMapping(path = "${te.account.service.transferFees.uri}", produces = APPLICATION_JSON_UTF8_VALUE,
            consumes = APPLICATION_JSON_UTF8_VALUE)
    TransferFeesResponse getTransferFees(@RequestParam String language,
                                         @RequestParam String unit,
                                         @RequestHeader String customerId,
                                         @RequestHeader String userId,
                                         @RequestBody TransferFeesRequest transferFeesRequest);
}