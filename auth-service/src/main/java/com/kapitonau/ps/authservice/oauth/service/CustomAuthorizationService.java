package com.kapitonau.ps.authservice.oauth.service;

import com.kapitonau.ps.authservice.exception.OAuth2AuthorizationServerException;
import com.kapitonau.ps.authservice.mapper.AuthorizationMapper;
import com.kapitonau.ps.authservice.model.AuthorizationModel;
import com.kapitonau.ps.authservice.model.ClientModel;
import com.kapitonau.ps.authservice.repository.AuthorityRepository;
import com.kapitonau.ps.authservice.repository.AuthorizationRepository;
import com.kapitonau.ps.authservice.repository.ClientRepository;
import com.kapitonau.ps.authservice.repository.custom.CustomClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationService implements OAuth2AuthorizationService {

    private final MessageSource messageSource;
    private final AuthorizationMapper authorizationMapper;
    private final AuthorizationRepository authorizationRepository;
    private final CustomClientRepository customClientRepository;

    @Override
    public void save(OAuth2Authorization authorization) {
        authorizationRepository.save(authorizationMapper.toModel(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
    }

    @Override
    public OAuth2Authorization findById(String id) {
        AuthorizationModel authorizationModel = authorizationRepository.findById(id)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "",
                        messageSource.getMessage("", null, getLocale())
                ));

        RegisteredClient registeredClient =
                customClientRepository.findById(authorizationModel.getRegisteredClientId().toString());

        return authorizationMapper.toResponse(authorizationModel, registeredClient);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return findAccessTokenByToken(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return findRefreshTokenTyToken(token);
        } else {
            throw new OAuth2AuthorizationServerException(
                    "",
                    messageSource.getMessage("", null, getLocale())
            );
        }
    }

    private OAuth2Authorization findAccessTokenByToken(String token) {
        AuthorizationModel authorizationModel = authorizationRepository.findByAccessTokenValue(token)
                .orElseThrow(() -> new OAuth2AuthorizationServerException("", messageSource.getMessage("", null, getLocale())));

        RegisteredClient registeredClient =
                customClientRepository.findById(authorizationModel.getRegisteredClientId().toString());
        return authorizationMapper.toResponse(authorizationModel, registeredClient);
    }

    private OAuth2Authorization findRefreshTokenTyToken(String token) {
        AuthorizationModel authorizationModel = authorizationRepository.findByRefreshTokenValue(token)
                .orElseThrow(() -> new OAuth2AuthorizationServerException("", messageSource.getMessage("", null, getLocale())));
        RegisteredClient registeredClient =
                customClientRepository.findById(authorizationModel.getRegisteredClientId().toString());
        return authorizationMapper.toResponse(authorizationModel, registeredClient);
    }
}
