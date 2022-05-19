package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.client.DisputeTransactionClient;
import com.neo.v1.dispute.model.DisputeCaseResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisputeTransactionServiceTest {
    private static final String CUSTOMER_ID = "5678548";
    private static final String USER_ID = "duaa@gmail.com";
    private static final String LANGUAGE = "en";
    private static final String COUNTRY = "IN";
    private static final String UNIT = "neo";

    @InjectMocks
    DisputeTransactionService subject;
    @Mock
    DisputeTransactionClient disputeTransactionClient;

    @BeforeAll
    static void setupContext() {
        GenericRestParamDto genericRestParam = GenericRestParamDto.builder()
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .locale(new Locale(LANGUAGE, COUNTRY))
                .unit(UNIT)
                .build();

        GenericRestParamContextHolder.setContext(genericRestParam);
    }

    @Test
    void whenDisputeCaseObjectisPassedasData_getDisputeDetail_() {

        String target = "Debit";
        DisputeCaseResponse expect = DisputeCaseResponse.builder()
                .build();
        when(disputeTransactionClient.getCreditDebitDetails(CUSTOMER_ID, target)).thenReturn(expect);
        DisputeCaseResponse response = subject.getDisputeDetail(target);
        assertThat(response).isEqualTo(expect);
    }
}