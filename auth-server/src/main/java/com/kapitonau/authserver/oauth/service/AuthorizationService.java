package com.kapitonau.authserver.oauth.service;

import com.kapitonau.authserver.exception.OAuth2AuthorizationServerException;
import com.kapitonau.authserver.oauth.mapper.AuthorizationMapper;
import com.kapitonau.authserver.oauth.repository.AuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements OAuth2AuthorizationService {

    private final MessageSource messageSource;
    private final AuthorizationMapper authorizationMapper;
    private final AuthorizationRepository authorizationRepository;

    @Override
    public void save(OAuth2Authorization authorization) {
        authorizationRepository.save(authorizationMapper.toModel(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorizationRepository.findById(Long.valueOf(id))
                .map(authorizationMapper::toResponse)
                .orElseThrow(() -> new OAuth2AuthorizationServerException(
                        "",
                        messageSource.getMessage("", null, getLocale())
                ));
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
        return authorizationRepository.findByAccessTokenValue(token)
                .map(authorizationMapper::toResponse)
                .orElseThrow(() -> new OAuth2AuthorizationServerException("", messageSource.getMessage("", null, getLocale())));
    }

    private OAuth2Authorization findRefreshTokenTyToken(String token) {
        return authorizationRepository.findByRefreshTokenValue(token)
                .map(authorizationMapper::toResponse)
                .orElseThrow(() -> new OAuth2AuthorizationServerException("", messageSource.getMessage("", null, getLocale())));
    }

}
