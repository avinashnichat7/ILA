package com.neo.v1.service;

import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class TransactionPaginationService {

    List<AccountTransaction> getPaginatedRecords(List<AccountTransaction> accountTransactionList, int offSet, int pageSize) {
        int totalRecords = accountTransactionList.size();
        if (isOffSetIndexOutOfBound(offSet, totalRecords)) {
            accountTransactionList = emptyList();
        } else {
            pageSize = (offSet + pageSize) > totalRecords ? totalRecords - offSet : pageSize;
            accountTransactionList = accountTransactionList.subList(offSet, offSet + pageSize);
        }
        return accountTransactionList;
    }

    private boolean isOffSetIndexOutOfBound(int offSet, int totalRecords) {
        return offSet > (totalRecords - 1);
    }
}
