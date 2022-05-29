package com.neo.v1.service;

import com.neo.v1.dispute.model.DisputeCaseObject;
import com.neo.v1.dispute.model.ListOfDisputeCasesDisput;
import com.neo.v1.mapper.DisputeTransactionMapper;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DisputeService {
    private final DisputeTransactionService disputeTransactionService;
    private final DisputeTransactionMapper disputeTransactionMapper;

    public List<AccountTransaction> filterDebitDisputeTransaction(List<AccountTransaction> transactions, String target) {
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = disputeTransactionService.getDisputeDetail(target).getData();
        Map<String, DisputeCaseObject> disputeCaseObjectMap = listOfDisputeCasesDisput.getListOfDisputeCasesDisput().stream()
                .collect(Collectors.toMap(DisputeCaseObject::getAccountId, Function.identity()));
        return filterDisputeCaseObject(transactions, disputeCaseObjectMap);
    }

    public List<AccountTransaction> filterCreditDisputeTransaction(List<AccountTransaction> transactions, String target) {
        ListOfDisputeCasesDisput listOfDisputeCasesDisput = disputeTransactionService.getDisputeDetail(target).getData();
        Map<String, DisputeCaseObject> disputeCaseObjectMap = listOfDisputeCasesDisput.getListOfDisputeCasesDisput().stream()
                .collect(Collectors.toMap(DisputeCaseObject::getPciNumber, Function.identity()));
        return filterDisputeCaseObject(transactions, disputeCaseObjectMap);
    }

    private List<AccountTransaction> filterDisputeCaseObject(List<AccountTransaction> transactions, Map<String, DisputeCaseObject> disputeCaseObjectMap) {
        return transactions.stream()
                .map(accountTransaction -> {
                    if (disputeCaseObjectMap.containsKey(accountTransaction.getId())) {
                        accountTransaction.setDispute(
                                disputeTransactionMapper.map(true, accountTransaction, disputeCaseObjectMap)
                        );
                    }
                    return accountTransaction;
                }).collect(Collectors.toList());
    }
}
