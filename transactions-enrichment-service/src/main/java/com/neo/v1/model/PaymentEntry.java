package com.neo.v1.model;

import com.neo.v1.tmsx.Amount;
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

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_AMOUNT_VALUE_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_POST_RELEASE_HOLD_CREDIT_ACCOUNT_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_REQUIRED_CODE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("PaymentEntry")
public class PaymentEntry {

    @NotBlank(message = TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE)
    @NotBlank(message = TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Account to create to hold on Always an IBAN", example = "861000191011", required = true)
    private String debitAccount;

    @NotBlank(message = TMSX_POST_RELEASE_HOLD_CREDIT_ACCOUNT_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Credit Account", example = "BH85ABCI00008786532323")
    private String creditAccount;

    @Valid
    @NotNull(message = TMSX_CREATE_HOLD_AMOUNT_VALUE_REQUIRED_CODE)
    @NotNull(message = TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Amount")
    private Amount amount;
}
