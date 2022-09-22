package com.neo.v1.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferFeesRequest {

    private String sourceAccountId;

    private String destinationAccountId;

    private BigDecimal amount;

    private String currency;

    private String transactionType;
}
