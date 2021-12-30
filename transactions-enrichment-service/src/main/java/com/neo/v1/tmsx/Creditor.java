package com.neo.v1.tmsx;

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
@ApiModel("Creditor")
public class Creditor {

    @ApiModelProperty(notes = "Creditor Account")
    private CreditorAccount account;

    @ApiModelProperty(notes = "Name", example = "Joe Black")
    private String name;

    @ApiModelProperty(notes = "Address", example = "Bahrain, Manama")
    private String address;
}
