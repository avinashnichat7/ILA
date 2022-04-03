package com.neo.v1.model.transactions;

import com.neo.core.model.ResponseMeta;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResponse {

    private ResponseMeta meta;
    private AccountTransaction data;
}