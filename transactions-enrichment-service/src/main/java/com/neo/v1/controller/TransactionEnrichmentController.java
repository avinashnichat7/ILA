package com.neo.v1.controller;


import com.neo.core.model.ApiError;
import com.neo.v1.service.TransactionEnrichmentService;
import com.neo.v1.transactions.enrichment.api.NeoServiceV1Api;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final TransactionEnrichmentService transactionsService;

    @Override
    @PostMapping
    public ResponseEntity<AccountTransactionsResponse> postTransactionEnrichment(AccountTransactionsRequest body) {
        return ResponseEntity.ok(transactionsService.getAccountTransactions(body));
    }

    @Override
    @GetMapping("/category")
    public ResponseEntity<CategoryListResponse> getTransactionEnrichmentCategory() {
        return ResponseEntity.ok(transactionsService.getMerchantCategoryList());
    }

    @Override
    @PostMapping("/category")
    public ResponseEntity<CreateCategoryResponse> postTransactionEnrichmentCategory(CreateCategoryRequest body) {
        return ResponseEntity.ok(transactionsService.createCategory(body));
    }
}