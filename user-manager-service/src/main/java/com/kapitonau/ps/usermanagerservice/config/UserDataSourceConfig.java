package com.kapitonau.ps.usermanagerservice.config;

import com.zaxxer.hikari.HikariDataSource;
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
        basePackages = {"com.kapitonau.ps.usermanagerservice"},
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
public class UserDataSourceConfig {

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.kapitonau.ps.usermanagerservice.model")
                .build();
    }

    @Bean("userTransactionManager")
    public PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManagerFactory")LocalContainerEntityManagerFactoryBean userEntityManagerFactory) {
        return new JpaTransactionManager(userEntityManagerFactory.getObject());
    }


    /*@Bean
    @Primary
    @ConfigurationProperties("spring.datasource.issuing-datasource")
    public DataSourceProperties issuingDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("issuingDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.issuing-datasource.configuration")
    public DataSource issuingDataSource() {
        return issuingDataSourceProperties().initializeDataSourceBuilder()
//                .type(CustomHikariDataSource.class)
            .build();
    }

    @Primary
    @Bean(name = "issuingEntityManagerFactory")
    @DependsOn("flywayConfig")
    public LocalContainerEntityManagerFactoryBean issuingEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(issuingDataSource())
                .packages("com.lwo.processing.issuingservice.model")
                .build();
    }


    @Primary
    @Bean(name = "issuingPlatformTransactionManager")
    public PlatformTransactionManager issuingTransactionManager(
            final @Qualifier("issuingEntityManagerFactory") LocalContainerEntityManagerFactoryBean issuingEntityManagerFactory) {
        return new JpaTransactionManager(issuingEntityManagerFactory.getObject());
    }
*/

}
