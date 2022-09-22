package com.neo.v1.mapper;

import com.neo.v1.model.transactions.TransactionDetailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionDetailRequestMapperTest {

    @InjectMocks
    private TransactionDetailRequestMapper transactionDetailRequestMapper;

    @Test
    void map_returnSuccess() {
        String accountId = "accountId";
        String transactionReference = "reference";
        TransactionDetailRequest expected = TransactionDetailRequest.builder().accountId(accountId).transactionReference(transactionReference).build();
        TransactionDetailRequest result = transactionDetailRequestMapper.map(accountId, transactionReference);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
