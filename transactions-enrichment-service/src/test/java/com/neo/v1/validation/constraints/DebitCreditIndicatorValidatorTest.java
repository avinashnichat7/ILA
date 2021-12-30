package com.neo.v1.validation.constraints;

import org.junit.jupiter.api.Test;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ALL_INDICATOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREDIT_INDICATOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DEBIT_INDICATOR;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCreditIndicatorValidatorTest {

    private DebitCreditIndicatorValidator subject = new DebitCreditIndicatorValidator();

    @Test
    public void whenIsValidInvokedWithDebitCreditIndicator_CREDIT_ExpectValidated() {
        String debitCreditIndicator = CREDIT_INDICATOR;
        assertTrue(subject.isValid(debitCreditIndicator, null));
    }

    @Test
    public void whenIsValidInvokedWithDebitCreditIndicator_DEBIT_ExpectValidated() {
        String debitCreditIndicator = DEBIT_INDICATOR;
        assertTrue(subject.isValid(debitCreditIndicator, null));
    }

    @Test
    public void whenIsValidInvokedWithDebitCreditIndicator_ALL_ExpectValidated() {
        String debitCreditIndicator = ALL_INDICATOR;
        assertTrue(subject.isValid(debitCreditIndicator, null));
    }

    @Test
    public void whenIsValidInvokedWithDebitCreditIndicator_EMPTY_ExpectValidated() {
        String debitCreditIndicator = "";
        assertTrue(subject.isValid(debitCreditIndicator, null));
    }

}