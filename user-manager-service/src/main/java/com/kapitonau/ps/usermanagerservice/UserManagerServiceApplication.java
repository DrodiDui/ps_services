package com.kapitonau.ps.usermanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.kapitonau.ps.usermanagerservice",
        "com.kapitonau.ps.commonspringlib",
        "com.kapitonau.ps.apirequestlib.bean"
})
@EnableFeignClients(basePackages = {"com.kapitonau.ps.apirequestlib.bean.client"})
public class UserManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerServiceApplication.class, args);
    }

}
