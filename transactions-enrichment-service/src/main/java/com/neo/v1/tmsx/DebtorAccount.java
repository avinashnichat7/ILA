package com.neo.v1.tmsx;

import com.neo.v1.validation.group.PostReleaseHoldValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DebtorAccount")
public class DebtorAccount {

    @NotBlank(message = TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE, groups = {PostReleaseHoldValidationGroup.OnReleaseHold.class, PostReleaseHoldValidationGroup.OnPostReleaseHold.class})
    @ApiModelProperty(notes = "iban", example = "BH93ABCI02187623858545")
    private String iban;
}