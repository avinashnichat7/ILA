package com.neo.v1.mapper;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.TransactionLinkRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerAccountTransactionCategoryEntityMapperTest {

    @InjectMocks
    private CustomerAccountTransactionCategoryEntityMapper subject;

    private static final String LANGUAGE = "en";
    private static final String UNIT = "neo";
    private static final String CUSTOMER_ID = "C1234";
    private static final String USER_ID = "U1234";

    @BeforeEach
    void before() {
        GenericRestParamDto genericRestParamDto = GenericRestParamDto.builder()
                .locale(new Locale(LANGUAGE))
                .unit(UNIT)
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .build();
        GenericRestParamContextHolder.setContext(genericRestParamDto);
    }

    @Test
    void map_returnSuccess() {
        String reference = "reference";
        String iban = "iban";
        LocalDateTime dateTime = LocalDateTime.now();
        AccountTransaction accountTransaction = AccountTransaction.builder().reference(reference).transactionDate(dateTime).build();
        TransactionLinkRequest request = TransactionLinkRequest.builder().iban(iban).build();
        CustomerAccountTransactionCategoryEntity result = subject.map(accountTransaction, "1", request, true);
        CustomerAccountTransactionCategoryEntity expected = CustomerAccountTransactionCategoryEntity.builder()
                .customerId(CUSTOMER_ID).isCustom(Boolean.TRUE).active(Boolean.TRUE).transactionDate(dateTime)
                .updatedDate(result.getUpdatedDate()).createdDate(result.getCreatedDate()).categoryId("1")
                .accountId(iban).transactionReference(reference).build();
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
