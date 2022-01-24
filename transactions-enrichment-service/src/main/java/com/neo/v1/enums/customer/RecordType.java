package com.neo.v1.enums.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

@Getter
public enum RecordType {

    PROSPECT("Prospect"),
    CUSTOMER("Customer");

    private String type;

    protected static final Map<String, RecordType> RECORD_BY_TYPE = of(RecordType.values())
            .collect(toMap(RecordType::getType, identity()));

    RecordType(String type) {
        this.type = type;
    }

    @Override
    @JsonValue
    public String toString() {
        return type;
    }

    @JsonCreator
    public static RecordType fromValue(String type) {
        return RECORD_BY_TYPE.get(type);
    }

}