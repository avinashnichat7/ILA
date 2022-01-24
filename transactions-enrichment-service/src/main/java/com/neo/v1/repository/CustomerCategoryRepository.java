package com.neo.v1.repository;

import com.neo.v1.entity.CustomerCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategoryEntity, Long> {

    List<CustomerCategoryEntity> findByCustomerId(String customerId);
}
