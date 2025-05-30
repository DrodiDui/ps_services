package com.kapitonau.gitservice.config

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
    basePackages = ["com.kapitonau.gitservice"],
    entityManagerFactoryRef = "gitEntityManagerFactory",
    transactionManagerRef = "gitTransaction"
)
class DatasourceConfig {
    @Bean
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("gitDatasource")
    fun dataSource(): DataSource? {
        return dataSourceProperties().initializeDataSourceBuilder()
            .build()
    }

    @Bean(name = ["gitEntityManagerFactory"])
    fun gitEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource())
            .packages("com.kapitonau.gitservice.model")
            .build()
    }


    @Bean(name = ["gitTransaction"])
    fun gitTransaction(
        @Qualifier("gitEntityManagerFactory") gitEntityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): PlatformTransactionManager {
        return JpaTransactionManager(gitEntityManagerFactory.getObject()!!)
    }
}
