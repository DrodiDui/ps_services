package com.kapitonau.ps.authservice.mapper;

import com.kapitonau.ps.authservice.model.AuthorizationModel;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.UUID;

@Component
public class AuthorizationMapper {
    public AuthorizationModel toModel(OAuth2Authorization authorization) {
        return AuthorizationModel.builder()
                .authorizationId(UUID.randomUUID().toString())
                .registeredClientId(Long.valueOf(authorization.getRegisteredClientId()))
                .authorizationGrantType(authorization.getAuthorizationGrantType().getValue())
                .authorizedScopes(authorization.getAuthorizedScopes().stream().reduce((s1, s2) -> s1 + " " + s2).orElse(""))
                .accessTokenValue(authorization.getAccessToken().getToken().getTokenValue())
                .accessTokenIssuedAt(authorization.getAccessToken().getToken().getIssuedAt().atZone(ZoneId.systemDefault()))
                .accessTokenExpiresAt(authorization.getAccessToken().getToken().getExpiresAt().atZone(ZoneId.systemDefault()))
                .refreshTokenValue(authorization.getRefreshToken().getToken().getTokenValue())
                .refreshTokenIssuedAt(authorization.getRefreshToken().getToken().getIssuedAt().atZone(ZoneId.systemDefault()))
                .refreshTokenExpiresAt(authorization.getRefreshToken().getToken().getExpiresAt().atZone(ZoneId.systemDefault()))
                .build();
    }

    public OAuth2Authorization toResponse(AuthorizationModel authorityModel, RegisteredClient registeredClient) {

        return OAuth2Authorization.withRegisteredClient(registeredClient)
                .accessToken(new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        authorityModel.getAccessTokenValue(),
                        authorityModel.getAccessTokenIssuedAt().toInstant(),
                        authorityModel.getAccessTokenExpiresAt().toInstant()
                ))
                .refreshToken(new OAuth2RefreshToken(
                        authorityModel.getRefreshTokenValue(),
                        authorityModel.getRefreshTokenIssuedAt().toInstant(),
                        authorityModel.getRefreshTokenExpiresAt().toInstant()
                ))
                .build();
    }
}
