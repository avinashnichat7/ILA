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
@ApiModel("PaymentIdentification")
public class PaymentIdentification {

    @ApiModelProperty(notes = "End to End", example = "PAYM0001")
    private String endToEndId;

    @ApiModelProperty(notes = "Transaction id", example = "2018120717540001")
    private String transactionId;
}
