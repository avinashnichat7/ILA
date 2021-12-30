package com.neo.v1.client;

import com.neo.v1.model.TransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "TransactionClient", url = "${te.transaction.service.base.url}")
public interface TransactionClient {

    @PostMapping(path = "/list", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    AccountTransactionsResponse getAccountTransactionsByStatus(@RequestHeader("customerId") String customerId,
                                                               @RequestHeader("userId") String userId,
                                                               @RequestParam("unit") String unit,
                                                               @RequestParam("language") String lang,
                                                               @RequestBody TransactionsRequest request,
                                                               @RequestParam("status") String status);
}
