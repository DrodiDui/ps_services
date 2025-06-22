package com.kapitonau.ps.refrenceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.kapitonau.ps.commonspringlib",
                /*"com.kapitonau.ps.apirequestlib",*/
                "com.kapitonau.ps.refrenceservice"
        }
)
public class RefrenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefrenceServiceApplication.class, args);
    }

}
