package com.neo.v1.repository;

import com.neo.v1.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {


    UnitEntity findByCode(String unit);
}
