package com.kapitonau.taskservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.kapitonau.taskservice",
        "com.kapitonau.commonspring",
        "com.kapitonau.projectstudio"
    ]
)
@EnableFeignClients(
    basePackages = [
        "com.kapitonau.projectstudio.bean.client",
        "com.kapitonau.taskservice.client"
    ]
)
class TaskServiceApplication

fun main(args: Array<String>) {
    runApplication<TaskServiceApplication>(*args)
}
