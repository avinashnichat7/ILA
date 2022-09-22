package com.neo.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

@AllArgsConstructor
@Getter
public enum TransferType {

    FAWRI("Fawri"),
    FAWRIPLUS("FawriPlus"),
    FAWATEER("Fawateer"),
    BETWEEN_MY_ACCOUNTS("BetweenMyAccounts"),
    BETWEEN_BANK_ACCOUNTS("BetweenBankAccounts"),
    DOMESTIC("Domestic"),
    INTERNATIONAL("International");

    private static final Map<String, TransferType> TRANSFER_TYPE_BY_CODE = of(TransferType.values()).collect(toMap(TransferType::getValue, identity()));

    private String value;

    @JsonCreator
    public static TransferType fromValue(String type) {
        return TRANSFER_TYPE_BY_CODE.get(type);
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}