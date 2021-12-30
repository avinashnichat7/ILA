package com.neo.v1.config;

import com.neo.core.model.DBPoolConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "te.urbis.datasource.hikari")
public class UrbisPoolConfig extends DBPoolConfigProperties {}
