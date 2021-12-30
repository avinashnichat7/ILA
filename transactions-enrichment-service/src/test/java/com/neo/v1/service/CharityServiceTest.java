package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.core.provider.ServiceKeyMapping;
import com.neo.v1.client.CharityClient;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.model.charity.CharityListResponse;
import feign.RetryableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.CHARITY_SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharityServiceTest {

    private static final String LANGUAGE = "en";
    private static final String UNIT = "neo";
    private static final String CUSTOMER_ID = "C1234";
    private static final String USER_ID = "U1234";

    @InjectMocks
    private CharityService subject;

    @Mock
    private CharityClient charityClient;

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

    @AfterEach
    void after() {
        GenericRestParamContextHolder.clearContext();
    }

    @Test
    void getCharityDetail_withCharityIdAndPurposeId_throwException() {
        Long charityId = 1l;
        Long purposeId = 1l;
        when(charityClient.getCharityById(CUSTOMER_ID, USER_ID, UNIT, LANGUAGE, charityId, purposeId)).thenThrow(RetryableException.class);

        ServiceKeyMapping keyMapping = assertThrows(ServiceException.class, () -> subject.getCharityDetail(charityId, purposeId)).getKeyMapping();
        assertEquals(keyMapping, CHARITY_SERVICE_UNAVAILABLE);

        verify(charityClient).getCharityById(CUSTOMER_ID, USER_ID, UNIT, LANGUAGE, charityId, purposeId);
    }

    @Test
    void getCharityDetail_withCharityIdAndPurposeId_returnCharityItemData() {
        Long charityId = 1l;
        Long purposeId = 1l;
        CharityListResponse charityListResponse = CharityListResponse.builder().data(CharityItemData.builder().build()).build();
        CharityItemData expected = charityListResponse.getData();

        when(charityClient.getCharityById(CUSTOMER_ID, USER_ID, UNIT, LANGUAGE, charityId, purposeId)).thenReturn(charityListResponse);

        CharityItemData result = subject.getCharityDetail(charityId, purposeId);
        assertThat(result).isEqualToComparingFieldByField(expected);

        verify(charityClient).getCharityById(CUSTOMER_ID, USER_ID, UNIT, LANGUAGE, charityId, purposeId);
    }

}
