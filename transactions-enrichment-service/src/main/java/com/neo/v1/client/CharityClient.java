package com.neo.v1.client;

import com.neo.v1.model.charity.CharityListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(name = "CharityClient", url = "${te.charity.service.base.url}")
public interface CharityClient {

    @GetMapping(path = "/{charityId}/{purposeId}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    CharityListResponse getCharityById(@RequestHeader("customerId") String customerId,
                                       @RequestHeader("userId") String userId,
                                       @RequestParam("unit") String unit,
                                       @RequestParam("language") String lang,
                                       @PathVariable(name = "charityId") Long charityId,
                                       @PathVariable(name = "purposeId") Long purposeId);

}