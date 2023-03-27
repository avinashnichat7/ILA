package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.TransactionClient;
import com.neo.v1.enums.GlobalConfig;
import com.neo.v1.mapper.TransactionDetailRequestMapper;
import com.neo.v1.mapper.TransactionsRequestMapper;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
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
    private final TransactionDetailRequestMapper transactionDetailRequestMapper;

    public List<AccountTransaction> getAccountTransactionsByStatus(AccountTransactionsRequest request, String status) {

        AccountTransactions accountTransactions;
        try {
            accountTransactions = transactionClient.getAccountTransactionsByStatus(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getUnit(),
                    getContext().getLocale().getLanguage(),
                    transactionsRequestMapper.map(request),
                    status).getData();
        } catch (RetryableException retryableException) {
            throw new ServiceException(TRANSACTION_SERVICE_UNAVAILABLE, retryableException);
        }
        return accountTransactions.getTransactions();
    }

    public AccountTransaction getTransactionDetail(String accountId, String transactionReference) {

        AccountTransaction accountTransaction;
        try {
            log.info("Calling getTransactionDetail with Account id {} and transactionReference {}", accountId, transactionReference);
            accountTransaction = transactionClient.getTransactionDetail(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getUnit(),
                    getContext().getLocale().getLanguage(),
                    transactionDetailRequestMapper.map(accountId, transactionReference)).getData();
        } catch (RetryableException retryableException) {
            throw new ServiceException(TRANSACTION_SERVICE_UNAVAILABLE, retryableException);
        }
        return accountTransaction;
    }


}