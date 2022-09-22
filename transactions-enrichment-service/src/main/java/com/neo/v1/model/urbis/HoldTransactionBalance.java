package com.neo.v1.model.urbis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "holdTransactionBalance", procedureName = "API_HoldTransactionBalance", resultClasses = {HoldTransactionBalance.class}, parameters = {
                @StoredProcedureParameter(name = "iban", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "holdNumber", type = String.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "error_code", type = Integer.class, mode = ParameterMode.INOUT),
                @StoredProcedureParameter(name = "error_message", type = String.class, mode = ParameterMode.INOUT)})
})
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HoldTransactionBalance {

    @Column(name = "available_balance_before")
    private String availableBalanceBefore;

    @Column(name = "available_balance_after")
    private String availableBalanceAfter;

    @Column(name = "account_currency")
    private String accountCurrency;

}
