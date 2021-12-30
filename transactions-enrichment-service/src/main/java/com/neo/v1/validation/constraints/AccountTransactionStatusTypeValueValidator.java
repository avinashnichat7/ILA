package com.neo.v1.validation.constraints;

import com.neo.v1.enums.AccountTransactionStatusType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AccountTransactionStatusTypeValueValidator implements ConstraintValidator<AccountTransactionStatusTypeValidator, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNull(value) || nonNull(AccountTransactionStatusType.forValue(value));
    }
}
