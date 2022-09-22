package com.neo.v1.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferFees {

    private TransferCurrency currency;

    private TransferCurrency accountCurrency;

    private BigDecimal calculatedAmount;

    private List<TransferCharge> charges;

}
