package com.neo.v1.repository;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerCreditTransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerCreditTransactionCategoryRepository extends JpaRepository<CustomerCreditTransactionCategoryEntity, Long> {

    List<CustomerCreditTransactionCategoryEntity> findByAccountIdAndCustomerIdAndTransactionDateBetween(String accountId, String customerId, LocalDateTime fromDate, LocalDateTime toDate);
}
