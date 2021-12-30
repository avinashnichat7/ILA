package com.neo.v1.tmsx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CreditorAccount")
public class CreditorAccount {

    @ApiModelProperty(notes = "IBAN", example = "BH93ABCI02187623858545")
    private String iban;
}
