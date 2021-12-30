package com.neo.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum AccountTransactionStatusType {

    COMPLETED("completed"),
    FAILED_PENDING("failed_pending"),
    PENDING("pending"),
    FAILED("failed");

    private static final Map<String, AccountTransactionStatusType> NAMES = stream(values())
            .collect(toMap(AccountTransactionStatusType::getValue, identity()));

    private final String value;

    @JsonCreator
    public static AccountTransactionStatusType forValue(String value) {
        return NAMES.get(value);
    }

    @JsonValue
    public String value() {
        return value;
    }
}
