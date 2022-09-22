package com.neo.v1.validation.constraints;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTransactionStatusTypeValueValidatorTest {

    private AccountTransactionStatusTypeValueValidator subject = new AccountTransactionStatusTypeValueValidator();

    @Test
    void isValid_withValueNull_returnTrue() {
        assertTrue(subject.isValid(null, null));
    }

    @Test
    void isValid_withValueInvalid_returnFalse() {
        assertFalse(subject.isValid("test", null));
    }

    @Test
    void isValid_withValueValid_returnTrue() {
        assertTrue(subject.isValid("completed", null));
    }
}
