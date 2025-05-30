package com.kapitonau.authserver.oauth.config;

import com.kapitonau.authserver.oauth.converter.OAuth2GrantTypePasswordRequestConverter;
import com.kapitonau.authserver.oauth.mapper.ClientMapper;
import com.kapitonau.authserver.oauth.provider.Oauth2PasswordAuthenticationProvider;
import com.kapitonau.authserver.oauth.repository.ClientRepository;
import com.kapitonau.authserver.oauth.repository.custom.CustomRegisteredClientRepository;
import com.kapitonau.authserver.oauth.service.AuthorizationService;
import com.kapitonau.authserver.oauth.service.CustomUserDetailService;
import com.kapitonau.authserver.oauth.token.CustomTokenGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

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
            CustomUserDetailService userDetailService,
            CustomTokenGenerator tokenGenerator,
            AuthorizationService authorizationService
    ) {
        return new Oauth2PasswordAuthenticationProvider(
                passwordEncoder,
                messageSource,
                userDetailService,
                tokenGenerator,
                authorizationService
        );
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }


    /*@Bean
    public void initClient(CustomRegisteredClientRepository clientRepository) {
        RegisteredClient demoClient = RegisteredClient.withId(String.valueOf(100001))
                .clientName("Demo client")
                .clientId("demo-client")
                .clientSecret("{noop}demo-secret")
                .redirectUri("http://localhost:8080/auth")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofMinutes(300))
                                .refreshTokenTimeToLive(Duration.ofMinutes(600))
                                .authorizationCodeTimeToLive(Duration.ofMinutes(20))
                                .reuseRefreshTokens(false)
                                .build()
                )
                .build();
        clientRepository.save(demoClient);
    }*/

}
