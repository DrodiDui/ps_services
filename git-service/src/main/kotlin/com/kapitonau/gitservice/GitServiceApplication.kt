package com.kapitonau.gitservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.kapitonau.gitservice",
        "com.kapitonau.commonspring",
        "com.kapitonau.projectstudio"
    ]
)
@EnableFeignClients(
    basePackages = [
        "com.kapitonau.projectstudio.bean.client",
        "com.kapitonau.gitservice.client.feign",
    ]
)
class GitServiceApplication

fun main(args: Array<String>) {
    runApplication<GitServiceApplication>(*args)
}
