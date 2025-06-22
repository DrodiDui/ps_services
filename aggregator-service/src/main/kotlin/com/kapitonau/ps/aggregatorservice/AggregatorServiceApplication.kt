package com.kapitonau.ps.aggregatorservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.kapitonau.ps.aggregatorservice",
        "com.kapitonau.ps.commonspringlib",
        "com.kapitonau.ps.apirequestlib.kafka",
        "com.kapitonau.ps.apirequestlib.bean"
    ]
)
@EnableFeignClients(basePackages = ["com.kapitonau.ps.apirequestlib.bean.client"])
class AggregatorServiceApplication

fun main(args: Array<String>) {
    runApplication<AggregatorServiceApplication>(*args)
}
