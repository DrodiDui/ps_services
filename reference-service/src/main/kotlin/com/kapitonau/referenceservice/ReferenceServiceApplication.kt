package com.kapitonau.referenceservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = [
    "com.kapitonau.referenceservice",
    "com.kapitonau.commonspring",
])
class ReferenceServiceApplication

fun main(args: Array<String>) {
    runApplication<ReferenceServiceApplication>(*args)
}
