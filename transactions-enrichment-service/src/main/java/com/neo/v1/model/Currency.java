package com.neo.v1.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@ApiModel("Currency")
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    private String code;

    private String decimalPlaces;
}
