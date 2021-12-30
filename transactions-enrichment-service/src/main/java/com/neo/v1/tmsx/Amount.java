package com.neo.v1.tmsx;

import com.neo.v1.validation.group.PostReleaseHoldValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_AMOUNT_VALUE_INVALID_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CREATE_HOLD_AMOUNT_VALUE_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_CURRENCY_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_CURRENCY_REQUIRED_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_REQUIRED_CODE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("Amount")
public class Amount {

    @ApiModelProperty(notes = "Currency ISO code", example = "BHD", required = true)
    @NotBlank(message = TMSX_CURRENCY_REQUIRED_CODE)
    @NotBlank(message = TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_CURRENCY_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    private String currency;

    @NotNull(message = TMSX_CREATE_HOLD_AMOUNT_VALUE_REQUIRED_CODE)
    @NotNull(message = TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_REQUIRED_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @Positive(message = TMSX_CREATE_HOLD_AMOUNT_VALUE_INVALID_CODE)
    @Positive(message = TMSX_CREATE_HOLD_AMOUNT_VALUE_INVALID_CODE, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Amount should be positive", example = "300", required = true)
    private BigDecimal value;
}
