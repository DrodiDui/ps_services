package com.kapitonau.ps.authservice.config;

import com.kapitonau.ps.authservice.oauth.OAuth2GrantTypePasswordRequestConverter;
import com.kapitonau.ps.authservice.oauth.provider.Oauth2PasswordAuthenticationProvider;
import com.kapitonau.ps.authservice.oauth.service.CustomAuthorizationService;
import com.kapitonau.ps.authservice.oauth.service.CustomTokenGeneratorService;
import com.kapitonau.ps.authservice.oauth.service.CustomUserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class OAuth2Config {


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain oauth2SecurityFilterChain(
            HttpSecurity http,
            MessageSource messageSource,
            Oauth2PasswordAuthenticationProvider passwordAuthenticationProvider,
            DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) -> {
                    authorizationServer
                            .tokenEndpoint(tokenEndpoint -> {
                                tokenEndpoint
                                        .accessTokenRequestConverter(new OAuth2GrantTypePasswordRequestConverter(messageSource))
                                        .authenticationProvider(passwordAuthenticationProvider);
                            });
                });


        return http.build();
    }

    @Bean
    public Oauth2PasswordAuthenticationProvider oauth2PasswordAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            MessageSource messageSource,
            CustomUserDetailsService userDetailService,
            CustomTokenGeneratorService tokenGenerator,
            CustomAuthorizationService authorizationService
    ) {
        return new Oauth2PasswordAuthenticationProvider(
                messageSource,
                userDetailService,
                passwordEncoder,
                tokenGenerator,
                authorizationService
        );
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailsService customUserDetailService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

}
