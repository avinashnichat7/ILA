package com.neo.v1.client;

import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "CreditCardClient", url = "${te.credit.service.base.url}")
public interface CreditCardClient {

    @GetMapping(path = "/transaction", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    CreditCardTransactionsResponse postCreditCardsTransactions(@RequestHeader("customerId") String customerId,
                                                               @RequestHeader("userId") String userId,
                                                               @RequestParam("unit") String unit,
                                                               @RequestParam("language") String lang,
                                                               @Validated CreditCardTransactionsRequest body);
}