package com.neo.v1.repository;

import com.neo.v1.entity.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long> {

    List<CustomerCategory> findByCustomerId(String customerId);
}
