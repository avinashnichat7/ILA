package com.neo.v1.mapper;

import com.neo.core.builder.ResponseMetaBuilder;
import com.neo.core.message.GenericMessageSource;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactions;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.Meta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_ACCOUNT_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountTransactionsResponseMapperTest {

    @InjectMocks
    private AccountTransactionsResponseMapper subject;

    @Mock
    private GenericMessageSource genericMessageSource;

    @Mock
    private ResponseMetaBuilder responseMetaBuilder;

    @Mock
    private AccountTransactionsMapper accountTransactionsMapper;

    @Test
    void whenMapIsCalledWithListOfAccountTransactionExpectSubjectToReturnAccountTransactionsResponse() {
        List<AccountTransaction> accountTransactionList = Stream.of(AccountTransaction.builder().build()).collect(Collectors.toList());
        AccountTransactions accountTransactions = AccountTransactions.builder().build();
        String message = "audit/display MESSAGE";

        Meta meta = Meta.builder()
                .code(TRANSACTIONS_ACCOUNT_CODE)
                .message(message)
                .build();

        when(genericMessageSource.getMessage(anyString(), any())).thenReturn(message);
        when(accountTransactionsMapper.map(accountTransactionList)).thenReturn(accountTransactions);

        AccountTransactionsResponse expected = AccountTransactionsResponse.builder()
                .data(accountTransactions)
                .meta(meta)
                .build();

        AccountTransactionsResponse result = subject.map(accountTransactionList);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(genericMessageSource).getMessage(anyString(), any());
        verify(accountTransactionsMapper).map(accountTransactionList);
    }
}
