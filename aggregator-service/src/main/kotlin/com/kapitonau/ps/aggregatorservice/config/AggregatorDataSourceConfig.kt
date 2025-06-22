package com.kapitonau.ps.aggregatorservice.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.kapitonau.ps.aggregatorservice"],
    entityManagerFactoryRef = "aggregatorEntityManagerFactory",
    transactionManagerRef = "aggregatorTransactionManager",
)
class AggregatorDataSourceConfig {

    @Bean
    fun datasourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("aggregatorDatasource")
    fun dataSource(): DataSource {
        return datasourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean("aggregatorEntityManagerFactory")
    fun workspaceEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource())
            .packages("com.kapitonau.ps.aggregatorservice.model")
            .build()
    }

    @Bean("aggregatorTransactionManager")
    fun aggregatorTransactionManager(@Qualifier("aggregatorEntityManagerFactory") aggregatorEntityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return JpaTransactionManager(aggregatorEntityManagerFactory.getObject()!!)
    }

}
