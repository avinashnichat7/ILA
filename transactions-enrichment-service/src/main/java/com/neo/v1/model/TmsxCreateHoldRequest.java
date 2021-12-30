package com.neo.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neo.core.serialization.CustomLocalDateDeserializer;
import com.neo.core.serialization.CustomLocalDateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_OPERATION_TYPE_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_TRANSACTION_ID_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_VALUE_DATE_REQUIRED_CODE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("ProxyCreateHoldRequest")
public class TmsxCreateHoldRequest {

    @NotBlank(message = TMSX_CREATE_HOLD_TRANSACTION_ID_REQUIRED_CODE)
    @ApiModelProperty(notes = "transaction id defined and provided by TMS/X", example = "2018120717540001", required = true)
    private String transactionId;

    @NotNull(message = TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE)
    @Valid
    @ApiModelProperty(notes = "Payment Entry")
    private PaymentEntry paymentEntry;

    @NotNull(message = TMSX_CREATE_HOLD_OPERATION_TYPE_REQUIRED_CODE)
    @Valid
    @ApiModelProperty(notes = "Payment Details")
    private PaymentDetails paymentDetails;

    @ApiModelProperty(notes = "Value Date", example = "2018-01-01", required = true)
    @NotNull(message = TMSX_CREATE_HOLD_VALUE_DATE_REQUIRED_CODE)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate valueDate;
}
