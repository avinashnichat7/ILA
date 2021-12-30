package com.neo.v1.repository;

import com.neo.v1.entity.ConfigurationCompositeKey;
import com.neo.v1.entity.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, ConfigurationCompositeKey> {
}
