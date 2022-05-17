package com.neo.v1.repository;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerAccountTransactionCategoryRepository extends JpaRepository<CustomerAccountTransactionCategoryEntity, Long> {

    List<CustomerAccountTransactionCategoryEntity> findByAccountIdAndCustomerIdAndTransactionDateBetween(String accountId, String customerId, LocalDateTime fromDate, LocalDateTime toDate);

    CustomerAccountTransactionCategoryEntity findByCustomerIdAndCategoryIdAndActive(String customerId, String categoryId, boolean isActive);
}
