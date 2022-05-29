package com.neo.v1.service;

import com.neo.v1.dispute.model.DisputeCaseObject;
import com.neo.v1.dispute.model.DisputeCaseResponse;
import com.neo.v1.dispute.model.ListOfDisputeCasesDisput;
import com.neo.v1.mapper.DisputeTransactionMapper;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.Dispute;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisputeServiceTest {

    @InjectMocks
    private DisputeService subject;
    @Mock
    DisputeTransactionService disputeTransactionService;
    @Mock
    DisputeTransactionMapper disputeTransactionMapper;

    @Test
    void WhenTargerIsDebit_addDebitDisputeTransaction_returnFilteredTransactionListWithDispute() {

        String target  = "debit";
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
                .accountId("XXXX")
                .build();
        List<DisputeCaseObject> disputeCaseObjectList = new ArrayList<>();
        disputeCaseObjectList.add(disputeCaseObject);
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = ListOfDisputeCasesDisput.builder()
                .listOfDisputeCasesDisput(disputeCaseObjectList)
                .build();
        DisputeCaseResponse disputeCaseResponse = DisputeCaseResponse.builder()
                .data(listOfDisputeCasesDisput)
                .build();
        List<AccountTransaction> accountTransactionList = new ArrayList<>();
        accountTransactionList.add(accountTransaction);
        Map<String, DisputeCaseObject> disputeCaseObjectMap = new HashMap<>();
        disputeCaseObjectMap.put(disputeCaseObject.getAccountId(), disputeCaseObject);
        when(disputeTransactionService.getDisputeDetail(target)).thenReturn(disputeCaseResponse);
        when(disputeTransactionMapper.map(true,accountTransaction,disputeCaseObjectMap)).thenReturn(dispute);
        List<AccountTransaction> response = subject.filterDebitDisputeTransaction(accountTransactionList, target);
        assertThat(response.get(0)).isEqualToComparingFieldByFieldRecursively(accountTransactionList.get(0));
    }

    @Test
    void WhenTargerIsDebit_addCreditDisputeTransaction_returnFilteredTransactionListWithDispute() {

        String target  = "credit";
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
                .pciNumber("XXXX")
                .build();
        List<DisputeCaseObject> disputeCaseObjectList = new ArrayList<>();
        disputeCaseObjectList.add(disputeCaseObject);
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = ListOfDisputeCasesDisput.builder()
                .listOfDisputeCasesDisput(disputeCaseObjectList)
                .build();
        DisputeCaseResponse disputeCaseResponse = DisputeCaseResponse.builder()
                .data(listOfDisputeCasesDisput)
                .build();
        List<AccountTransaction> accountTransactionList = new ArrayList<>();
        accountTransactionList.add(accountTransaction);
        Map<String, DisputeCaseObject> disputeCaseObjectMap = new HashMap<>();
        disputeCaseObjectMap.put(disputeCaseObject.getPciNumber(), disputeCaseObject);
        when(disputeTransactionService.getDisputeDetail(target)).thenReturn(disputeCaseResponse);
        when(disputeTransactionMapper.map(true,accountTransaction,disputeCaseObjectMap)).thenReturn(dispute);
        List<AccountTransaction> response = subject.filterCreditDisputeTransaction(accountTransactionList, target);
        assertThat(response.get(0)).isEqualToComparingFieldByFieldRecursively(accountTransactionList.get(0));
    }
}