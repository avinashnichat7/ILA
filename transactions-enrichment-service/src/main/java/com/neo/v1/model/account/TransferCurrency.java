package com.neo.v1.model.account;

import com.neo.v1.model.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransferCurrency extends Currency {

    private String description;

    @Builder(builderMethodName = "currencyBuilder")
    public TransferCurrency(String code, String decimalPlaces, String description) {
        super(code, decimalPlaces);
        this.description = description;
    }
}
