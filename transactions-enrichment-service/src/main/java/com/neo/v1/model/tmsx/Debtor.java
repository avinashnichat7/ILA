package com.neo.v1.model.tmsx;

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
import javax.validation.constraints.NotNull;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("Debtor")
public class Debtor {

    @Valid
    @NotNull(message = TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE, groups = {PostReleaseHoldValidationGroup.OnReleaseHold.class, PostReleaseHoldValidationGroup.OnPostReleaseHold.class})
    @ApiModelProperty(notes = "Debtor Account")
    private DebtorAccount account;

    @ApiModelProperty(notes = "Name", example = "Joe Black")
    private String name;

    @ApiModelProperty(notes = "Address", example = "Bahrain, Manama")
    private String address;
}
