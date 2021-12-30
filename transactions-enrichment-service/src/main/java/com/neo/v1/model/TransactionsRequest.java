package com.neo.v1.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neo.core.serialization.CustomLocalDateDeserializer;
import com.neo.core.serialization.CustomLocalDateSerializer;
import com.neo.v1.validation.constraints.ValidDebitCreditIndicator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTIONS_INVALID_DEBIT_CREDIT_INDICATOR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTIONS_INVALID_FILTER_SIZE_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTIONS_INVALID_FROM_AMOUNT_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTIONS_INVALID_TO_AMOUNT_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTIONS_REQUIRED_ACCOUNT_ID_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_ACCOUNT_TRANSACTION_FROM_DATE_DEFAULT_MONTHS_TO_MINUS;
import static java.time.LocalDate.now;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("TransactionsRequest")
public class TransactionsRequest {

    @NotBlank(message = GET_ACCOUNT_TRANSACTIONS_REQUIRED_ACCOUNT_ID_CODE)
    @ApiModelProperty(notes = "Account Id", example = "46443234", required = true)
    private String id;

    @ApiModelProperty(notes = "Filter by transaction description", example = "Restaurant meal")
    @Size(message = GET_ACCOUNT_TRANSACTIONS_INVALID_FILTER_SIZE_CODE, max = 255)
    private String filter;

    @ApiModelProperty(notes = "Transaction above this amount")
    @PositiveOrZero(message = GET_ACCOUNT_TRANSACTIONS_INVALID_FROM_AMOUNT_CODE)
    private BigDecimal fromAmount;

    @ApiModelProperty(notes = "Transaction below this amount")
    @PositiveOrZero(message = GET_ACCOUNT_TRANSACTIONS_INVALID_TO_AMOUNT_CODE)
    private BigDecimal toAmount;

    @ApiModelProperty(notes = "Transaction starting from this date")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate fromDate = now().minusMonths(GET_ACCOUNT_TRANSACTION_FROM_DATE_DEFAULT_MONTHS_TO_MINUS);

    @ApiModelProperty(notes = "Transaction bellow this date")
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate toDate = now();

    @ValidDebitCreditIndicator(message = GET_ACCOUNT_TRANSACTIONS_INVALID_DEBIT_CREDIT_INDICATOR_CODE)
    @ApiModelProperty(notes = "Credit or debit transaction, negative or positive transaction" +
                              "Possible values: debit, credit, all", allowableValues = "debit, credit, all")
    private String debitCreditIndicator;

}
