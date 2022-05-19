package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.client.DisputeTransactionClient;
import com.neo.v1.dispute.model.DisputeCaseResponse;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.DISPUTE_TRANSACTION;

@Slf4j
@Service
@AllArgsConstructor
public class DisputeTransactionService {
    private final DisputeTransactionClient disputeTransactionClient;
    DisputeCaseResponse getDisputeDetail(String target) {
        GenericRestParamDto context = getContext();
        try {
            return disputeTransactionClient.getCreditDebitDetails(
                    context.getCustomerId(),
                    target);
        } catch (RetryableException retryableException) {
            throw new ServiceException(DISPUTE_TRANSACTION, retryableException);
        }
    }

}
