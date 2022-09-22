package com.neo.v1.config;

import com.neo.core.builder.DatabasePoolConfigBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static com.neo.v1.constants.TransactionEnrichmentConstants.URBIS_DB_PERSISTENCE_UNIT;
import static com.neo.v1.constants.TransactionEnrichmentConstants.URBIS_REPOSITORY_PACKAGE;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "urbisEntityManagerFactory", transactionManagerRef = "urbisTransactionManager", basePackages = { URBIS_REPOSITORY_PACKAGE })
public class UrbisDbConfig {

    @Bean(name = "urbisDataSource")
    @ConfigurationProperties(prefix = "te.urbis.datasource")
    public DataSource dataSource(UrbisPoolConfig poolConfig, DatabasePoolConfigBuilder databasePoolConfigBuilder) {
        return databasePoolConfigBuilder.build(poolConfig);
    }

    @Bean(name = "urbisEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean urbisEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("urbisDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.neo.v1.entity.urbis")
                .persistenceUnit(URBIS_DB_PERSISTENCE_UNIT)
                .build();
    }

    @Bean(name = "urbisTransactionManager")
    public PlatformTransactionManager urbisTransactionManager(@Qualifier("urbisEntityManagerFactory") EntityManagerFactory urbisEntityManagerFactory) {
        return new JpaTransactionManager(urbisEntityManagerFactory);
    }
}
