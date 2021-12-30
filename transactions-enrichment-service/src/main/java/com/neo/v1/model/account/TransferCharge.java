package com.neo.v1.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferCharge {

    private String chargeTypeCode;

    private BigDecimal chargeAmount;

    private String chargeFormat;

    private BigDecimal chargeAmountInAccountCurrency;

    private String vatTypeCode;

    private BigDecimal vatAmount;

    private String vatFormat;

    private BigDecimal vatAmountInAccountCurrency;

    private BigDecimal exchangeRate;

}
