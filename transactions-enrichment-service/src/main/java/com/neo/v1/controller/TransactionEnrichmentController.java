package com.neo.v1.controller;


import com.neo.core.model.ApiError;
import com.neo.v1.service.TransactionEnrichmentService;
import com.neo.v1.transactions.enrichment.api.NeoServiceV1Api;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import com.neo.v1.transactions.enrichment.model.DeleteCategoryResponse;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions-enrichment")
@ApiResponses({
        @ApiResponse(code = 400, message = "BadRequest", response = ApiError.class),
        @ApiResponse(code = 403, message = "Forbidden", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
})
public class TransactionEnrichmentController implements NeoServiceV1Api {

    private final TransactionEnrichmentService transactionsService;

    @Override
    @PostMapping
    public ResponseEntity<AccountTransactionsResponse> postTransactionsEnrichment(AccountTransactionsRequest body) {
        return ResponseEntity.ok(transactionsService.getAccountTransactions(body));
    }

    @Override
    @GetMapping("/category")
    public ResponseEntity<CategoryListResponse> getTransactionsEnrichmentCategory() {
        return ResponseEntity.ok(transactionsService.getMerchantCategoryList());
    }

    @Override
    @PostMapping("/category")
    public ResponseEntity<CreateCategoryResponse> postTransactionsEnrichmentCategory(CreateCategoryRequest body) {
        return ResponseEntity.ok(transactionsService.createCategory(body));
    }

    @Override
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<UpdateCategoryResponse> putTransactionsEnrichmentCategory(Long categoryId, UpdateCategoryRequest body) {
        return ResponseEntity.ok(transactionsService.updateCategory(categoryId, body));
    }

    @Override
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<DeleteCategoryResponse> deleteTransactionsEnrichmentCategory(Long categoryId) {
        return ResponseEntity.ok(transactionsService.deleteCategory(categoryId));
    }
}