package com.kapitonau.ps.taskservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.kapitonau.ps.taskservice",
        "com.kapitonau.ps.commonspringlib",
        "com.kapitonau.ps.apirequestlib.kafka",
        "com.kapitonau.ps.apirequestlib.bean"
    ]
)
@EnableFeignClients(basePackages = ["com.kapitonau.ps.apirequestlib.bean.client"])
class TaskServiceApplication

fun main(args: Array<String>) {
    runApplication<TaskServiceApplication>(*args)
}
