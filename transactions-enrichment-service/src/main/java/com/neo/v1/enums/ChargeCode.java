package com.neo.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum ChargeCode {
    NCHG("NCHG", "NO CHARGE"),
    CACR("CACR", "CLIENT ACCOUNT CREDIT"),
    CADB("CADB", "CLIENT ACCOUNT DEBIT"),
    CR07("CR07", "CREDIT CHG 07"),
    CR20("CR20", "CREDIT CHG 20"),
    DB07("DB07", "DEBIT CHG 07"),
    DB20("DB20", "DEBIT CHG 20"),
    LTRN("LTRN", "TRANSACTION CODE FOR LAS"),
    M100("M100", "MTHLY MAINTENANCE CHG"),
    PV01("PV01", "PL PROVISION DEBIT"),
    PV02("PV02", "PL PROVISION CREDIT"),
    PV04("PV04", "BS PROVISION  WRITE OFF");

    private static final Map<String, ChargeCode> NAMES = stream(values())
            .collect(toMap(ChargeCode::getCode, identity()));

    private final String code;

    private final String description;

    @JsonCreator
    public static ChargeCode forValue(String value) {
        return NAMES.get(value);
    }

}
