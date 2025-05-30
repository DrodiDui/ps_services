package com.kapitonau.authserver.oauth.token;

import com.kapitonau.authserver.model.AuthorityModel;
import com.kapitonau.authserver.model.RoleModel;
import com.kapitonau.authserver.model.UserModel;
import com.kapitonau.authserver.oauth.common.OAuth2GrantPasswordAuthorizationToken;
import com.kapitonau.authserver.repository.UserRepository;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.*;

@Component
public class CustomTokenGenerator implements OAuth2TokenGenerator<OAuth2Token> {

    private final JWKSource<SecurityContext> jwkSource;
    private final OAuth2AccessTokenCustomizer accessTokenCustomizer;
    private final UserRepository userRepository;

    public CustomTokenGenerator(
            JWKSource<SecurityContext> jwkSource,
            OAuth2AccessTokenCustomizer accessTokenCustomizer,
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


    /*public OAuth2AccessToken generateToken(OAuth2TokenContext context) {
        String issuer = null;
        if (context.getAuthorizationServerContext() != null) {
            issuer = context.getAuthorizationServerContext().getIssuer();
        }

        RegisteredClient registeredClient = context.getRegisteredClient();
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(Duration.ofSeconds(Long.parseLong(registeredClient.getTokenSettings().getSetting("access-token-time-to-live").toString())));
        OAuth2TokenClaimsSet.Builder claimsBuilder = OAuth2TokenClaimsSet.builder();
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }

        claimsBuilder.subject(context.getPrincipal().getName()).audience(Collections.singletonList(registeredClient.getClientId())).issuedAt(issuedAt).expiresAt(expiresAt).notBefore(issuedAt).id(UUID.randomUUID().toString());
        if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
            claimsBuilder.claim("scope", context.getAuthorizedScopes());
        }
        OAuth2TokenClaimsSet accessTokenClaimsSet = claimsBuilder.build();
        return (OAuth2AccessToken) new OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType.BEARER, this.accessTokenGenerator.generateKey(), accessTokenClaimsSet.getIssuedAt(), accessTokenClaimsSet.getExpiresAt(), context.getAuthorizedScopes(), accessTokenClaimsSet.getClaims());
    }*/

    /*public OAuth2RefreshToken generateRefreshToken(OAuth2TokenContext context) {
        Instant issuedAt = Instant.now();
        Duration ttl = Duration.ofSeconds(Long.valueOf(context.getRegisteredClient().getTokenSettings().getSetting("refresh-token-time-to-live").toString()));
        Instant expiresAt = issuedAt.plus(ttl);
        return new OAuth2RefreshToken(this.refreshTokenGenerator.generateKey(), issuedAt, expiresAt);
    }*/

    /*private static final class OAuth2AccessTokenClaims extends OAuth2AccessToken implements ClaimAccessor {
        private final Map<String, Object> claims;

        private OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt, Set<String> scopes, Map<String, Object> claims) {
            super(tokenType, tokenValue, issuedAt, expiresAt, scopes);
            this.claims = claims;
        }

        public Map<String, Object> getClaims() {
            return this.claims;
        }
    }*/

}
