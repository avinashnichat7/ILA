package com.neo.v1.mapper;

import com.neo.v1.dispute.model.DisputeCaseObject;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.Dispute;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class DisputeTransactionMapper {
    public Dispute map(boolean isDisputed, AccountTransaction accountTransaction, Map<String, DisputeCaseObject> disputeCaseObjectMap) {
        return Dispute.builder()
                .isDisputed(isDisputed)
                .crmCaseId(disputeCaseObjectMap.get(accountTransaction.getId()).getCrmCaseId())
                .reportDateTime(disputeCaseObjectMap.get(accountTransaction.getId()).getReportDateTime())
                .build();
    }
}
