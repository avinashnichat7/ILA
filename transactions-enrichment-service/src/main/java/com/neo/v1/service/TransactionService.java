package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.TransactionClient;
import com.neo.v1.mapper.TransactionsRequestMapper;
import com.neo.v1.model.TransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.TRANSACTION_SERVICE_UNAVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionClient transactionClient;
    private final TransactionsRequestMapper transactionsRequestMapper;


    public List<AccountTransaction> getAccountTransactionsByStatus(AccountTransactionsRequest request, String status) {

        AccountTransactions accountTransactions;
        try {
            accountTransactions = transactionClient.getAccountTransactionsByStatus(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getLocale().getLanguage(),
                    getContext().getUnit(),
                    transactionsRequestMapper.map(request),
                    status).getData();
        } catch (RetryableException retryableException) {
            throw new ServiceException(TRANSACTION_SERVICE_UNAVAILABLE, retryableException);
        }
        return accountTransactions.getTransactions();
    }
}