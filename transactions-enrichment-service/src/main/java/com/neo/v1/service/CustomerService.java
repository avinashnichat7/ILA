package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.core.model.GenericRestParamDto;
import com.neo.v1.client.CustomerClient;
import com.neo.v1.model.customer.CustomerDetailData;
import com.neo.v1.model.customer.CustomerDetailResponse;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.CUSTOMER_SERVICE_UNAVAILABLE;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerClient customerClient;

    CustomerDetailData getCustomerDetail() {
        CustomerDetailResponse customerDetail;
        GenericRestParamDto context = getContext();
        try {
            customerDetail = customerClient.getCustomerDetail(
                    context.getCustomerId(),
                    context.getUserId(),
                    context.getLocale().getLanguage(),
                    context.getUnit());
            log.info("Customer response received: [{}]", customerDetail);
        } catch (RetryableException retryableException) {
            throw new ServiceException(CUSTOMER_SERVICE_UNAVAILABLE, retryableException);
        }

        return customerDetail.getData();
    }

}