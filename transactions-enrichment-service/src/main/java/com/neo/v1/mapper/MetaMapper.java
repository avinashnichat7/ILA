package com.neo.v1.mapper;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.transactions.enrichment.model.Meta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetaMapper {

    private final GenericMessageSource messageSource;

    public Meta map(String responseCode, String messageKey, Object... params) {
        return Meta.builder()
                .code(responseCode)
                .message(messageSource.getMessage(messageKey, params))
                .build();
    }
}
