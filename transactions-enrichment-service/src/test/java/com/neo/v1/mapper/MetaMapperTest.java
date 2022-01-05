package com.neo.v1.mapper;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.transactions.enrichment.model.Meta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MetaMapperTest {

    @InjectMocks
    private MetaMapper subject;

    @Mock
    private GenericMessageSource messageSource;

    @Test
    void mapForMeta_withValidParameters_returnMeta() {

        String code = GET_CATEGORY_LIST_SUCCESS_CODE;
        String message = GET_CATEGORY_LIST_SUCCESS_MSG;
        String messageKey = "com.neo.message.key";
        Meta expected = Meta.builder()
                .code(code)
                .message(message)
                .build();
        when(messageSource.getMessage(eq(messageKey))).thenReturn(message);

        Meta actual = subject.map(code, messageKey);
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
        verify(messageSource).getMessage(eq(messageKey));
    }
}
