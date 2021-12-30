package com.neo.v1.validation;

import com.neo.v1.enums.TransactionEntryType;
import com.neo.v1.model.EntryRequest;
import com.neo.v1.model.TransactionEntry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class TransactionEntryValidator implements ConstraintValidator<ValidTransactionEntry, EntryRequest> {

    private static final List<TransactionEntryType> ENTRY_TYPES = asList(TransactionEntryType.values());

    @Override
    public boolean isValid(EntryRequest value, ConstraintValidatorContext context) {
        if (isNull(value.getEntries()) || value.getEntries().isEmpty()) {
            return false;
        }
        return value.getEntries().stream().map(TransactionEntry::getEntryType).collect(toList())
                .containsAll(ENTRY_TYPES);
    }
}
