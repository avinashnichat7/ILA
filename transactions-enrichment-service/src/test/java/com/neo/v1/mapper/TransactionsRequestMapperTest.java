package com.neo.v1.mapper;

import com.neo.v1.model.TransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TransactionsRequestMapperTest {

    @InjectMocks
    private TransactionsRequestMapper subject;

    @Test
    void map_returnTransactionsRequest() {
        AccountTransactionsRequest request = AccountTransactionsRequest.builder()
                .id("1")
                .filter("filter")
                .fromAmount(BigDecimal.ONE)
                .toAmount(BigDecimal.ONE)
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now())
                .debitCreditIndicator("debitCardIndicator")
                .build();
        TransactionsRequest expected = TransactionsRequest.builder()
                .id(request.getId())
                .filter(request.getFilter())
                .fromAmount(request.getFromAmount())
                .toAmount(request.getToAmount())
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .debitCreditIndicator(request.getDebitCreditIndicator())
                .build();
        TransactionsRequest result = subject.map(request);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

}
