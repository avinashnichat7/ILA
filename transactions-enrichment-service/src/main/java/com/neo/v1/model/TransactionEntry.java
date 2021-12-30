package com.neo.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neo.core.serialization.CustomLocalDateDeserializer;
import com.neo.core.serialization.CustomLocalDateSerializer;
import com.neo.core.validation.groups.ValidationGroup.OnCreate;
import com.neo.v1.enums.ChargeCode;
import com.neo.v1.enums.NarrativeCode;
import com.neo.v1.enums.TransactionEntryType;
import com.neo.v1.validation.group.PostReleaseHoldValidationGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_ACCOUNT_ID_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_AMOUNT_INVALID;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_AMOUNT_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_CURRENCY_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_NARRATIVE_LENGTH;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CODE_ENTRY_TYPE_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_ACCOUNT_ID_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_AMOUNT_INVALID;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_AMOUNT_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_CURRENCY_REQUIRED;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_NARRATIVE_LENGTH;
import static com.neo.v1.constants.TransactionEnrichmentConstants.POST_RELEASE_HOLD_ENTRY_TYPE_REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEntry {

    @ApiModelProperty(notes = "Id of transaction type", example = "NMSC", required = true)
    private String transactionTypeId;

    @NotBlank(message = CODE_ENTRY_ACCOUNT_ID_REQUIRED, groups = OnCreate.class)
    @NotBlank(message = POST_RELEASE_HOLD_ENTRY_ACCOUNT_ID_REQUIRED, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Account Id", example = "1010100", required = true)
    private String accountId;

    @NotNull(message = CODE_ENTRY_AMOUNT_REQUIRED, groups = OnCreate.class)
    @NotNull(message = POST_RELEASE_HOLD_ENTRY_AMOUNT_REQUIRED, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @DecimalMin(value = "0.0", inclusive = false, groups = OnCreate.class, message = CODE_ENTRY_AMOUNT_INVALID)
    @DecimalMin(value = "0.0", inclusive = false, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class, message = POST_RELEASE_HOLD_ENTRY_AMOUNT_INVALID)
    @ApiModelProperty(notes = "Transaction Amount", required = true)
    private BigDecimal amount;

    @NotBlank(message = CODE_ENTRY_CURRENCY_REQUIRED, groups = OnCreate.class)
    @NotBlank(message = POST_RELEASE_HOLD_ENTRY_CURRENCY_REQUIRED, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Amount Currency", example = "10.00", required = true)
    private String amountCurrency;

    @NotNull(message = CODE_ENTRY_TYPE_REQUIRED, groups = OnCreate.class)
    @NotNull(message = POST_RELEASE_HOLD_ENTRY_TYPE_REQUIRED, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class)
    @ApiModelProperty(notes = "Entry Type", example = "Credit/Debit", required = true)
    private TransactionEntryType entryType;

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @ApiModelProperty(notes = "Date of transaction", example = "MM-dd-yyyy")
    private LocalDate valueDate;

    @ApiModelProperty(notes = "Reference id for reversal transaction")
    private String reversalNumber;

    @ApiModelProperty(notes = "Charge Code", example = "NCHG")
    private ChargeCode chargeCode;

    @ApiModelProperty(notes = "Narrative code", example = "TBD")
    private NarrativeCode narrativeCode;

    @ApiModelProperty(notes = "Transaction Information")
    private List<@Size(max = 34, message = CODE_ENTRY_NARRATIVE_LENGTH, groups = OnCreate.class) @Size(max = 34, message = POST_RELEASE_HOLD_ENTRY_NARRATIVE_LENGTH, groups = PostReleaseHoldValidationGroup.OnPostReleaseHold.class) String> narrativeLines;

    private String accountingCenter;
}
