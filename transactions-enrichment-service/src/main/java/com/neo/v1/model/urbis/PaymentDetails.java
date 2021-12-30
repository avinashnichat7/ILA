package com.neo.v1.model.urbis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neo.core.serialization.CustomLocalDateDeserializer;
import com.neo.core.serialization.CustomLocalDateSerializer;
import com.neo.v1.serialization.CustomLocalDateTimeZoneDeserializer;
import com.neo.v1.serialization.CustomLocalDateTimeZoneSerializer;
import com.neo.v1.tmsx.Amount;
import com.neo.v1.tmsx.BillDetail;
import com.neo.v1.tmsx.Creditor;
import com.neo.v1.tmsx.CreditorDebtorAgent;
import com.neo.v1.tmsx.Debtor;
import com.neo.v1.tmsx.PaymentIdentification;
import com.neo.v1.validation.group.PostReleaseHoldValidationGroup;
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
import java.time.ZonedDateTime;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_OPERATION_TYPE_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_POST_RELEASE_HOLD_OPERATION_TYPE_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("PaymentDetails")
public class PaymentDetails {

    @ApiModelProperty(notes = "Payment Identification")
    private PaymentIdentification paymentIdentification;

    @ApiModelProperty(notes = "Opration Type", example = "EBPP-NRT")
    @NotBlank(message = TMSX_POST_RELEASE_HOLD_OPERATION_TYPE_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @NotBlank(message = TMSX_CREATE_HOLD_OPERATION_TYPE_REQUIRED_CODE)
    private String operationType;

    @ApiModelProperty(notes = "Transaction Type Code", example = "002")
    private String transactionTypeCode;

    @ApiModelProperty(notes = "Value Date", example = "2018-01-01")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate valueDate;

    @ApiModelProperty(notes = "Creation Date", example = "2018-12-10T12:04:02")
    @JsonDeserialize(using = CustomLocalDateTimeZoneDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeZoneSerializer.class)
    private ZonedDateTime creationDate;

    @ApiModelProperty(notes = "Instructed Amount")
    private Amount instructedAmount;

    @Valid
    @NotNull(message = TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE, groups = {PostReleaseHoldValidationGroup.OnReleaseHold.class, PostReleaseHoldValidationGroup.OnPostReleaseHold.class})
    @ApiModelProperty(notes = "Debtor")
    private Debtor debtor;

    @ApiModelProperty(notes = "Debtor Agent")
    private CreditorDebtorAgent debtorAgent;

    @ApiModelProperty("Creditor")
    private Creditor creditor;

    @ApiModelProperty("Creditor Agent")
    private CreditorDebtorAgent creditorAgent;

    @ApiModelProperty(notes = "Regulatory Reporting", example = "goods purchase")
    private String regulatoryReporting;

    @ApiModelProperty(notes = "Remittance information.", example = "Salary payment")
    private String remittanceInformation;

    @ApiModelProperty(notes = "Instruction For Next Agent", example = "sender to receiver information")
    private String instructionForNextAgent;

    @ApiModelProperty(notes = "Biller Details", example = "Information about biller")
    private BillDetail billDetails;

    @ApiModelProperty(notes = "Local status", example = "CREATED")
    private String localStatus;

    @ApiModelProperty(notes = "status", example = "REJECTED")
    private String status;

    private String cbsStatus;
    private String reconciliationStatus;
}
