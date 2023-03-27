package com.neo.v1.repository;

import com.neo.v1.entity.ConfigurationEntity;
import com.neo.v1.entity.ConfigurationIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, ConfigurationIdentity> {
}
