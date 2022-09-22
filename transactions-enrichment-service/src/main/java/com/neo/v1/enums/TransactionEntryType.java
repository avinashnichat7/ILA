package com.neo.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PACKAGE;

@Getter
@AllArgsConstructor(access = PACKAGE)
public enum TransactionEntryType {
    CREDIT("credit", "C"),
    DEBIT("debit", "D");

    private static final Map<String, TransactionEntryType> NAMES = stream(values())
            .collect(toMap(TransactionEntryType::getValue, identity()));

    private static final Map<String, TransactionEntryType> BATCH_NAMES = stream(values())
            .collect(toMap(TransactionEntryType::getBatchValue, identity()));

    private final String value;

    private final String batchValue;

    @JsonCreator
    public static TransactionEntryType forValue(String value) {
        return NAMES.get(value.toLowerCase());
    }

    public static TransactionEntryType forBatchValue(String value) {
        return BATCH_NAMES.get(value);
    }

    @JsonValue
    public String value() {
        return value.toLowerCase();
    }
}
