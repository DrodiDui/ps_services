package com.kapitonau.ps.workspaceservice.config

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
    basePackages = ["com.kapitonau.ps.workspaceservice"],
    entityManagerFactoryRef = "workspaceEntityManagerFactory",
    transactionManagerRef = "workspaceTransactionManager",
)
class WorkspaceDatasourceConfig {

    @Bean
    fun datasourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("workspaceDatasource")
    fun dataSource(): DataSource {
        return datasourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Bean("workspaceEntityManagerFactory")
    fun workspaceEntityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource())
            .packages("com.kapitonau.ps.workspaceservice.model")
            .build()
    }

    @Bean("workspaceTransactionManager")
    fun workspaceTransactionManager(@Qualifier("workspaceEntityManagerFactory") workspaceEntityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager {
        return JpaTransactionManager(workspaceEntityManagerFactory.getObject()!!)
    }

}