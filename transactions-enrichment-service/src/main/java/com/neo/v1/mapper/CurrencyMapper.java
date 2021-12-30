package com.neo.v1.mapper;

import com.neo.v1.transactions.enrichment.model.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    Currency map(String currencyCode, String decimalPlaces) {
        return Currency.builder()
                .code(currencyCode)
                .decimalPlaces(decimalPlaces)
                .build();
    }
}