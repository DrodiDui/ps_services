package com.kapitonau.usermanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.kapitonau.usermanagerservice",
        "com.kapitonau.commonspring",
        "com.kapitonau.projectstudio"
})
@EnableFeignClients(
        basePackages = {
                "com.kapitonau.projectstudio.bean.client"
        }
)
public class UserManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerServiceApplication.class, args);
    }

}
