package com.neo.v1.repository;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerAccountTransactionCategoryCustomRepository {

    List<CustomerAccountTransactionCategoryEntity> findByAccountIdAndCustomerIdAndTransactionDateBetween(String accountId, String customerId, LocalDateTime fromDate, LocalDateTime toDate);
}
