package com.neo.v1.mapper;

import com.neo.v1.transactions.enrichment.model.Currency;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CurrencyMapperTest {

    private CurrencyMapper subject = new CurrencyMapper();

    @Test
    void mapCurrency_whenCalledWithCodeAndDecimalPlaces_shouldReturnCurrency() {
        String currencyCode = "BHD";
        String decimalPlaces = "3";

        Currency expected = Currency.builder()
                .code(currencyCode)
                .decimalPlaces(decimalPlaces)
                .build();

        Currency result = subject.map(currencyCode, decimalPlaces);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
