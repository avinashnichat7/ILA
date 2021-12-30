package com.neo.v1.model.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Serializable {

    private String code;

    private Integer decimalPlaces;

    private String description;

    private String nostro;

    private String swiftCode;

    private LocalTime cutOffTime;

    private boolean cutOffSameValueDate;
}
