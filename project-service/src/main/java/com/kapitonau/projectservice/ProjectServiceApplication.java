package com.kapitonau.projectservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.kapitonau.projectservice",
        "com.kapitonau.commonspring",
        "com.kapitonau.projectstudio"
})
@EnableFeignClients(
        basePackages = {
                "com.kapitonau.projectstudio.bean.client"
        }
)
public class ProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectServiceApplication.class, args);
    }

}
