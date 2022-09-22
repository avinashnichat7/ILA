package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.client.CharityClient;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.model.charity.CharityListResponse;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.CHARITY_SERVICE_UNAVAILABLE;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharityService {

    private final CharityClient charityClient;

    public CharityItemData getCharityDetail(Long charityId, Long purposeId) {
        CharityListResponse charity;
        try {
            charity = charityClient.getCharityById(
                    getContext().getCustomerId(),
                    getContext().getUserId(),
                    getContext().getUnit(),
                    getContext().getLocale().getLanguage(),
                    charityId,
                    purposeId);
            log.info("CharityById response received: [{}]", charity);
        } catch (RetryableException retryableException) {
            throw new ServiceException(CHARITY_SERVICE_UNAVAILABLE, retryableException);
        }
        return charity.getData();
    }
}
