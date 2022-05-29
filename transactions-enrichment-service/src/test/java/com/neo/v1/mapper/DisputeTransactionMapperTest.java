package com.neo.v1.mapper;

import com.neo.v1.dispute.model.DisputeCaseObject;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.Dispute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DisputeTransactionMapperTest {

    @InjectMocks
    DisputeTransactionMapper subject;

    @Test
    void map() {
        BigDecimal positiveAmount = BigDecimal.valueOf(20);
        Dispute dispute = Dispute.builder()
                .isDisputed(true)
                .crmCaseId("XXXX")
                .reportDateTime("XXXX")
                .build();
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .id("XXXX")
                .originalAmount(positiveAmount)
                .amount(positiveAmount)
                .dispute(dispute)
                .build();
        DisputeCaseObject disputeCaseObject = DisputeCaseObject.builder()
                .crmCaseId("XXXX")
                .reportDateTime("XXXX")
                .accountId("XXXX")
                .build();
        List<DisputeCaseObject> disputeCaseObjectList = new ArrayList<>();
        disputeCaseObjectList.add(disputeCaseObject);
        Map<String, DisputeCaseObject> disputeCaseObjectMap = new HashMap<>();
        disputeCaseObjectMap.put(disputeCaseObject.getAccountId(), disputeCaseObject);
        Dispute result = subject.map(true,accountTransaction,disputeCaseObjectMap);

        assertThat(dispute).isEqualToComparingFieldByFieldRecursively(result);

    }
}