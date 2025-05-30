package com.kapitonau.usermanagerservice.config;

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
        basePackages = "com.kapitonau.usermanagerservice",
        entityManagerFactoryRef = "userManagerEntityManagerFactory",
        transactionManagerRef = "userManagerTransaction"
)
public class DatasourceConfig {

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("userManagerDatasource")
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "userManagerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean projectEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.kapitonau.usermanagerservice.model")
                .build();
    }


    @Bean(name = "userManagerTransaction")
    public PlatformTransactionManager userManagerTransaction(
            final @Qualifier("userManagerEntityManagerFactory") LocalContainerEntityManagerFactoryBean projectEntityManagerFactory) {
        return new JpaTransactionManager(projectEntityManagerFactory.getObject());
    }

}
