package com.neo.v1.repository;

import com.neo.v1.entity.CustomerCreditTransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCreditTransactionCategoryRepository extends JpaRepository<CustomerCreditTransactionCategoryEntity, Long> {
}
