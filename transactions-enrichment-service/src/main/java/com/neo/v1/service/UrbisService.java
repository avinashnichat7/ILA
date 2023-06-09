package com.neo.v1.service;

import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionHoldEntity;
import com.neo.v1.reader.PropertyReader;
import com.neo.v1.repository.urbis.TransactionsRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.TransactionHoldRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class UrbisService {

    private final PropertyReader propertyReader;

    private final TransactionsRepository transactionsRepository;

    private final ConfigurationService configurationService;

    public List<AccountTransactionEntity> getAccountTransactions(String customerId, AccountTransactionsRequest request) {
        return transactionsRepository.getAccountTransactions(customerId, request);
    }

    public List<AccountPendingTransactionEntity> getPendingAccountTransactions(String customerId,
                                                                               AccountTransactionsRequest request) {
        return transactionsRepository.getPendingAccountTransactions(customerId, request, propertyReader.getPendingTransactionsPageSize());
    }
    
    public List<AccountTransactionHoldEntity> getAccountTransactionsHold(String customerId, TransactionHoldRequest request) {
        return transactionsRepository.getAccountTransactionsHold(customerId, request);
    }
    public List<AccountTransactionEntity> getAllAccountTransactions(String customerId,
                                                                    AccountTransactionsRequest request) {
        return transactionsRepository.getAllAccountTransactions(customerId, request);
    }
}