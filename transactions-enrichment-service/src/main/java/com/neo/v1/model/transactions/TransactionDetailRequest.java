package com.neo.v1.model.transactions;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TransactionDetailRequest {
    private String accountId;
    private String transactionReference;
}