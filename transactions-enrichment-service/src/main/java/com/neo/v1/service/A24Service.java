package com.neo.v1.service;

import com.neo.v1.entity.a24.A24AccountTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;

import com.neo.v1.repository.a24.A24TransactionsRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class A24Service {
    private final A24TransactionsRepository a24TransactionsRepository;


    public List<A24AccountTransactionEntity> getAccountTransactions(String customerId, AccountTransactionsRequest request) {
        return a24TransactionsRepository.getAccountTransactions(customerId, request);
    }

    }
