package com.kapitonau.ps.authservice.oauth.service;

import com.kapitonau.ps.authservice.repository.UserRepository;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenGeneratorService implements OAuth2TokenGenerator<OAuth2Token> {

    private final JWKSource<SecurityContext> jwkSource;
    private final OAuth2AccessTokenCustomizerService accessTokenCustomizer;
    private final UserRepository userRepository;

    public CustomTokenGeneratorService(
            JWKSource<SecurityContext> jwkSource,
            OAuth2AccessTokenCustomizerService accessTokenCustomizer,
            UserRepository userRepository
    ) {
        this.jwkSource = jwkSource;
        this.accessTokenCustomizer = accessTokenCustomizer;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2Token generate(OAuth2TokenContext context) {
        NimbusJwtEncoder encoder = new NimbusJwtEncoder(jwkSource);
        JwtGenerator jwtGenerator = new JwtGenerator(encoder);
        jwtGenerator.setJwtCustomizer(accessTokenCustomizer);
        //CustomOAuth2AccessTokenGenerator accessTokenGenerator = new CustomOAuth2AccessTokenGenerator();
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        //CustomOAuth2RefreshTokenGenerator refreshTokenGenerator = new CustomOAuth2RefreshTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();

        DelegatingOAuth2TokenGenerator delegatingOAuth2TokenGenerator =
                new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator , refreshTokenGenerator);

        return delegatingOAuth2TokenGenerator.generate(context);
    }

}
