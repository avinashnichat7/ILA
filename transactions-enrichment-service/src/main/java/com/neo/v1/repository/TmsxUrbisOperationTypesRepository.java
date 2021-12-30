package com.neo.v1.repository;

import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsxUrbisOperationTypesRepository extends JpaRepository<TmsxUrbisOperationTypesEntity, Long> {
}
