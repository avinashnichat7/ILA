package com.neo.v1.service;

import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionPaginationServiceTest {

    private TransactionPaginationService subject = new TransactionPaginationService();

    @Test
    void getPaginatedRecords_withAccountTransactionListAndOffSetAndPageSize_returnListOfAccountTransaction() {
        AccountTransaction accountTransaction1 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction2 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction3 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction4 = AccountTransaction.builder().build();

        List<AccountTransaction> accountTransactionList = Stream.of(accountTransaction1, accountTransaction2, accountTransaction3, accountTransaction4).collect(Collectors.toList());
        int offSet = 1;
        int pageSize = 2;

        List<AccountTransaction> expected = Stream.of(accountTransaction2, accountTransaction3).collect(Collectors.toList());

        List<AccountTransaction> result = subject.getPaginatedRecords(accountTransactionList, offSet, pageSize);
        assertThat(expected).isEqualTo(result);
    }

    @Test
    void getPaginatedRecords_withAccountTransactionListAndInvalidOffSetAndPageSize_returnListOfAccountTransaction() {
        AccountTransaction accountTransaction1 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction2 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction3 = AccountTransaction.builder().build();
        AccountTransaction accountTransaction4 = AccountTransaction.builder().build();

        List<AccountTransaction> accountTransactionList = Stream.of(accountTransaction1, accountTransaction2, accountTransaction3, accountTransaction4).collect(Collectors.toList());
        int offSet = 4;
        int pageSize = 2;

        List<AccountTransaction> result = subject.getPaginatedRecords(accountTransactionList, offSet, pageSize);
        assertThat(result.size()).isEqualTo(0);
    }
}
