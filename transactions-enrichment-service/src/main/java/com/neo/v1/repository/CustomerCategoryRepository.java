package com.neo.v1.repository;

import com.neo.v1.entity.CustomerCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategoryEntity, Long> {

    List<CustomerCategoryEntity> findByCustomerIdAndActive(String customerId, boolean isActive);

    Optional<CustomerCategoryEntity> findByIdAndCustomerIdAndActive(Long id, String customerId, boolean isActive);

    CustomerCategoryEntity findByIdAndActive(Long id, boolean isActive);
}
