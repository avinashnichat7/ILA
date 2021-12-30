package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.AccountClient;
import com.neo.v1.model.account.TransferFeesRequest;
import com.neo.v1.model.account.TransferFeesResponse;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.ACCOUNT_SERVICE_DOWN;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

    private final AccountClient accountClient;

    TransferFeesResponse getFees(TransferFeesRequest transferFeesRequest) {
        TransferFeesResponse transferFeesResponse;
        try {
            transferFeesResponse = accountClient.getTransferFees(
                    getContext().getLocale().getLanguage(),
                    getContext().getUnit(),
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    transferFeesRequest);
        } catch (RetryableException retryableException) {
            throw new ServiceException(ACCOUNT_SERVICE_DOWN, retryableException);
        }
        return transferFeesResponse;
    }
}
