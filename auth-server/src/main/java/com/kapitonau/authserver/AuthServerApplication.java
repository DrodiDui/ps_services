package com.kapitonau.authserver;

import com.kapitonau.authserver.oauth.repository.custom.CustomRegisteredClientRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

    private CustomRegisteredClientRepository customRegisteredClientRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
