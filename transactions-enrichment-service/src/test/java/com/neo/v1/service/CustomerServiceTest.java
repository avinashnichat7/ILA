package com.neo.v1.service;

import com.neo.core.context.GenericRestParamContextHolder;
import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.client.CustomerClient;
import com.neo.v1.model.customer.CustomerDetailData;
import com.neo.v1.model.customer.CustomerDetailResponse;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static com.neo.v1.enums.TransactionsServiceKeyMapping.CUSTOMER_SERVICE_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final String CUSTOMER_ID = "5678548";
    private static final String USER_ID = "duaa@gmail.com";
    private static final String LANGUAGE = "en";
    private static final String COUNTRY = "IN";
    private static final String UNIT = "neo";

    @InjectMocks
    private CustomerService subject;

    @Mock
    private CustomerClient customerClient;

    @BeforeAll
    static void setupContext() {
        GenericRestParamDto genericRestParam = GenericRestParamDto.builder()
                .customerId(CUSTOMER_ID)
                .userId(USER_ID)
                .locale(new Locale(LANGUAGE, COUNTRY))
                .unit(UNIT)
                .build();

        GenericRestParamContextHolder.setContext(genericRestParam);
    }


    @Test
    void getCustomerDetail_IsCalled_ReturnCustomerDetailData() {
        CustomerDetailData customerDetailData = CustomerDetailData.builder().build();
        CustomerDetailResponse customerDetailResponse = CustomerDetailResponse.builder()
                .data(customerDetailData)
                .build();

        when(customerClient.getCustomerDetail(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenReturn(customerDetailResponse);

        CustomerDetailData result = subject.getCustomerDetail();
        assertThat(customerDetailData).isEqualTo(result);

        verify(customerClient).getCustomerDetail(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }

    @Test
    void getCustomerDetail_ThrowRetryableException_throwServiceException() {
        RetryableException retryableException = mock(RetryableException.class);
        when(customerClient.getCustomerDetail(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT)).thenThrow(retryableException);

        ServiceException keyMapping = assertThrows(ServiceException.class, () -> subject.getCustomerDetail());
        assertThat(keyMapping.getKeyMapping()).isEqualTo(CUSTOMER_SERVICE_UNAVAILABLE);

        verify(customerClient).getCustomerDetail(CUSTOMER_ID, USER_ID, LANGUAGE, UNIT);
    }
}
