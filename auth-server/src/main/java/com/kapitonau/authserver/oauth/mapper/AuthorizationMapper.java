package com.kapitonau.authserver.oauth.mapper;

import com.kapitonau.authserver.exception.OAuth2AuthorizationServerException;
import com.kapitonau.authserver.oauth.common.AuthorizationGrantTypePassword;
import com.kapitonau.authserver.oauth.common.OAuth2GrantPasswordAuthorizationToken;
import com.kapitonau.authserver.oauth.model.AuthorizationModel;
import com.kapitonau.authserver.oauth.model.RegisteredClientModel;
import com.kapitonau.authserver.oauth.repository.ClientRepository;
import com.kapitonau.authserver.oauth.repository.custom.CustomRegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class AuthorizationMapper {

    private final CustomRegisteredClientRepository  clientRepository;

    public AuthorizationModel toModel(OAuth2Authorization authorization) {
        return AuthorizationModel.builder()
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

    public OAuth2Authorization toResponse(AuthorizationModel authorityModel) {

        RegisteredClient registeredClient = clientRepository.findById(authorityModel.getRegisteredClientId().toString());

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
