package com.neo.v1.repository;

import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerMerchantCategoryRepository extends JpaRepository<CustomerMerchantCategoryEntity, Long> {
    List<CustomerMerchantCategoryEntity> findByCustomerIdAndActive(String customerId, boolean isActive);
    CustomerMerchantCategoryEntity findByCustomerIdAndName(String customerId, String name);
}
