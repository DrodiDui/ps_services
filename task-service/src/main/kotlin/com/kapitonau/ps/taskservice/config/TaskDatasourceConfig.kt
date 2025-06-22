package com.kapitonau.ps.taskservice.config

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
    basePackages = ["com.kapitonau.ps.taskservice"],
    entityManagerFactoryRef = "taskEntityManagerFactory",
    transactionManagerRef = "taskTransactionManager",
)
class TaskDatasourceConfig {

    @Bean
    fun datasourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("taskDatasource")
    fun dataSource(): DataSource {
        return datasourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean("taskEntityManagerFactory")
    fun taskEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource())
            .packages("com.kapitonau.ps.taskservice.model")
            .build()
    }

    @Bean("taskTransactionManager")
    fun taskTransactionManager(@Qualifier("taskEntityManagerFactory") taskEntityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return JpaTransactionManager(taskEntityManagerFactory.getObject()!!)
    }

}