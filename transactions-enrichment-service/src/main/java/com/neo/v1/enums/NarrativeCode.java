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
public enum NarrativeCode {
    OTC("OTC", ""),
    OTD("OTD", ""),
    BCH("BCH", "Banking charges"),
    CDT("CDT", "Cash deposit"),
    CWL("CWL", "Cash withdrawal"),
    DXR("DXR", "Deposited cheque return"),
    EPD("EPD", "Electronic payment deposit"),
    EPW("EPW", "Electronic payment withdrawal"),
    INP("INP", "Interest paid"),
    ITD("ITD", "Internal transfer deposit"),
    ITW("ITW", "Internal transfer withdrawal"),
    OBL("OBL", "Opening balance"),
    PLT("PLT", "Year end standing order"),
    SAL("SAL", "Salary payment"),
    TRD("TRD", "Transfer deposit"),
    TRW("TRW", "Transfer withdrawal"),
    XDT("XDT", "Cheque deposit"),
    XLC("XLC", "Local cheque deposit"),
    XNC("XNC", "Non-local cheque deposit"),
    XWL("XWL", "Cheque withdrawal"),
    OTH("OTH", "");

    private static final Map<String, NarrativeCode> NAMES = stream(values())
            .collect(toMap(NarrativeCode::getCode, identity()));
    private final String code;
    private final String description;

    @JsonCreator
    public static NarrativeCode forValue(String value) {
        return NAMES.get(value);
    }
}
