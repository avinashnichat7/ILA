package com.neo.v1.service;

import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.reader.PropertyReader;
import com.neo.v1.repository.urbis.TransactionsRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor

public class UrbisService {

    private final PropertyReader propertyReader;
    private final TransactionsRepository transactionsRepository;

    public List<AccountTransactionEntity> getAccountTransactions(String customerId, AccountTransactionsRequest request) {
        return transactionsRepository.getAccountTransactions(customerId, request);
    }

    public List<AccountPendingTransactionEntity> getPendingAccountTransactions(String customerId,
                                                                               AccountTransactionsRequest request) {
        return transactionsRepository.getPendingAccountTransactions(customerId, request, propertyReader.getPendingTransactionsPageSize());
    }

}