package com.neo.v1.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ALL_INDICATOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREDIT_INDICATOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DEBIT_INDICATOR;
import static org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DebitCreditIndicatorValidator implements ConstraintValidator<ValidDebitCreditIndicator, String> {

    @Override
    public boolean isValid(String indicator, ConstraintValidatorContext constraintValidatorContext) {
        return isBlank(indicator) || equalsAnyIgnoreCase(indicator, CREDIT_INDICATOR, DEBIT_INDICATOR, ALL_INDICATOR);
    }
}
