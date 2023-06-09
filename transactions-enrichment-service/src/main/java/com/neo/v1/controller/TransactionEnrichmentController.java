package com.neo.v1.controller;


import com.neo.core.model.ApiError;

import com.neo.v1.service.TransactionEnrichmentService;
import com.neo.v1.transactions.enrichment.api.NeoServiceV1Api;
import com.neo.v1.transactions.enrichment.model.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    @Override
    @PostMapping("/link")
    public ResponseEntity<TransactionLinkResponse> postTransactionsEnrichmentLink(TransactionLinkRequest body) {
        return ResponseEntity.ok(transactionsService.link(body));
    }

	@Override
	@PostMapping("/hold")
	public ResponseEntity<TransactionHoldResponse> postTransactionsEnrichmentHold(TransactionHoldRequest transactionHoldRequest) {
		return ResponseEntity.ok(transactionsService.hold(transactionHoldRequest));
	}

    @Override
    @PostMapping("/credit-cards/transactions")
    public ResponseEntity<CreditCardTransactionsResponse> postTransactionsEnrichmentCreditCardsTransactions(CreditCardTransactionsRequest body) {
        return ResponseEntity.ok(transactionsService.creditCardTransactions(body));
    }
}