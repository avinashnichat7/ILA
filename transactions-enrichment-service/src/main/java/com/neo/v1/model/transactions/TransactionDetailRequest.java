package com.neo.v1.model.transactions;

import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TransactionDetailRequest {
    private String accountId;
    private String transactionReference;
}