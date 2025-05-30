package com.kapitonau.projectservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.kapitonau.projectservice",
        entityManagerFactoryRef = "projectEntityManagerFactory",
        transactionManagerRef = "projectPlatformTransactionManager"
)
public class DatasourceConfig {

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("issuingDatasource")
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "projectEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean projectEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.kapitonau.projectservice.model")
                .build();
    }


    @Bean(name = "projectPlatformTransactionManager")
    public PlatformTransactionManager issuingTransactionManager(
            final @Qualifier("projectEntityManagerFactory") LocalContainerEntityManagerFactoryBean projectEntityManagerFactory) {
        return new JpaTransactionManager(projectEntityManagerFactory.getObject());
    }

}
