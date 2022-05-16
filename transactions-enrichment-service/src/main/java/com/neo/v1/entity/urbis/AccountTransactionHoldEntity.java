package com.neo.v1.entity.urbis;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "accountTransactionsHold",
                procedureName = "API_GetHoldTransactions",
                resultClasses = {AccountTransactionHoldEntity.class},
                parameters = {
                        @StoredProcedureParameter(
                                name = "id",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "customer_id",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "offset",
                                type = Integer.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "page_size",
                                type = Integer.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "filter",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "from_date",
                                type = LocalDate.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "to_date",
                                type = LocalDate.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "from_amount",
                                type = BigDecimal.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "to_amount",
                                type = BigDecimal.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "exclude_card_transactions",
                                type = Boolean.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "target_type",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "masked_card_number",
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                name = "error_code",
                                type = Integer.class,
                                mode = ParameterMode.INOUT),
                        @StoredProcedureParameter(
                                name = "error_message",
                                type = String.class,
                                mode = ParameterMode.INOUT)})
})
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionHoldEntity {
	
	@Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transaction_value_date")
    private LocalDate valueDate;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_currency")
    private String transactionCurrency;

    @Column(name = "transaction_currency_decimal_places")
    private String transactionCurrencyPlaces;

    @Column(name = "transaction_exchange_rate")
    private BigDecimal transactionExchangeRate;

    @Column(name = "account_currency")
    private String accountCurrency;

    @Column(name = "account_currency_decimal_places")
    private String accountCurrencyPlaces;

    @Column(name = "transaction_description_1")
    private String transactionDescription1;

    @Column(name = "transaction_description_2")
    private String transactionDescription2;

    @Column(name = "transaction_description_3")
    private String transactionDescription3;

    @Column(name = "transaction_description_4")
    private String transactionDescription4;

    @Column(name = "transaction_description_5")
    private String transactionDescription5;

    @Column(name = "transaction_description_6")
    private String transactionDescription6;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "original_amount")
    private BigDecimal originalAmount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "previous_balance")
    private BigDecimal previousBalance;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Column(name = "generate_advice")
    private Integer generateAdvice;

    @Column(name = "status")
    private String status;
    
    @Column(name = "hold_expiry_date")
    private LocalDate holdExpiryDate;

}
