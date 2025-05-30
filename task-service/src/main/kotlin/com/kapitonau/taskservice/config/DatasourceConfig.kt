package com.kapitonau.taskservice.config

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
    basePackages = ["com.kapitonau.taskservice"],
    entityManagerFactoryRef = "taskEntityManagerFactory",
    transactionManagerRef = "taskPlatformTransactionManager"
)
class DatasourceConfig {
    @Bean
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("taskDatasource")
    fun dataSource(): DataSource? {
        return dataSourceProperties().initializeDataSourceBuilder()
            .build()
    }

    @Bean(name = ["taskEntityManagerFactory"])
    fun taskEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource())
            .packages("com.kapitonau.taskservice.model")
            .build()
    }


    @Bean(name = ["taskPlatformTransactionManager"])
    fun issuingTransactionManager(
        @Qualifier("taskEntityManagerFactory") taskEntityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        return JpaTransactionManager(taskEntityManagerFactory.getObject()!!)
    }
}
