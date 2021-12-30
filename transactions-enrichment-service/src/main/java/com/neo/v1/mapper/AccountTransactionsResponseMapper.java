package com.neo.v1.mapper;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.Meta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_ACCOUNT_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_ACCOUNT_MESSAGE;

@Component
@AllArgsConstructor
public class AccountTransactionsResponseMapper {

    private final GenericMessageSource genericMessageSource;
    private final AccountTransactionsMapper accountTransactionsMapper;

    public AccountTransactionsResponse map(List<AccountTransaction> accountTransactions) {
        return AccountTransactionsResponse.builder()
                .data(accountTransactionsMapper.map(accountTransactions))
                .meta(mapResponseMeta())
                .build();
    }

    private Meta mapResponseMeta() {
        return Meta.builder().code(TRANSACTIONS_ACCOUNT_CODE).message(genericMessageSource.getMessage(TRANSACTIONS_ACCOUNT_MESSAGE)).build();
    }
}
