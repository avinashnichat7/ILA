package com.neo.v1.entity.urbis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "accountPendingTransactions",
                procedureName = "API_AccountPendingTransaction",
                resultClasses = {AccountPendingTransactionEntity.class},
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
                                name = "debit_credit_indicator",
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
public class AccountPendingTransactionEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "transactionDate")
    private LocalDateTime transactionDate;

    @Column(name = "transactionValueDate")
    private LocalDate valueDate;

    @Column(name = "transactionType")
    private String transactionType;

    @Column(name = "transaction_currency")
    private String transactionCurrency;

    @Column(name = "transaction_currency_decimal_places")
    private String transactionCurrencyPlaces;

    @Column(name = "transactionExchangeRate")
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

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "mc_code")
    private String mcCode;
}
