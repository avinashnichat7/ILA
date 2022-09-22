package com.neo.v1.model.tmsx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("CreditorDebtorAgent")
public class CreditorDebtorAgent {

    @ApiModelProperty(notes = "bic", example = "ABCIBHBM")
    private String bic;
}
