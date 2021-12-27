package com.neo.v1.controller;


import com.neo.core.model.ApiError;

import com.neo.v1.transactions.enrichment.api.NeoServiceV1Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction-enrichment")
@ApiResponses({
        @ApiResponse(code = 400, message = "BadRequest", response = ApiError.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
})
public class TransactionEnrichmentController implements NeoServiceV1Api {

    @Override
    @GetMapping
    public ResponseEntity<Void> getTransactionEnrichment() {
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
}