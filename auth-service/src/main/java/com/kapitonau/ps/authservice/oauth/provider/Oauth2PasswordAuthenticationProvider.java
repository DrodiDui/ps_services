package com.kapitonau.ps.authservice.oauth.provider;

import com.kapitonau.ps.authservice.exception.OAuth2AuthorizationServerException;
import com.kapitonau.ps.authservice.oauth.AuthorizationGrantTypePassword;
import com.kapitonau.ps.authservice.oauth.CustomUserDetails;
import com.kapitonau.ps.authservice.oauth.OAuth2GrantPasswordAuthorizationToken;
import com.kapitonau.ps.authservice.oauth.service.CustomTokenGeneratorService;
import com.kapitonau.ps.authservice.util.OAuth2Utils;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Objects;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@AllArgsConstructor
public class Oauth2PasswordAuthenticationProvider implements AuthenticationProvider {

    public static final AuthorizationGrantType AUTHORIZATION_GRANT_TYPE_PASSWORD = AuthorizationGrantTypePassword.PASSWORD;

    private final MessageSource messageSource;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomTokenGeneratorService customTokenGenerator;
    private final OAuth2AuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2GrantPasswordAuthorizationToken passwordAuthentication =
                (OAuth2GrantPasswordAuthorizationToken) authentication;

        OAuth2ClientAuthenticationToken clientAuthenticationToken =
                getClientAuthenticationToken(passwordAuthentication);

        RegisteredClient registeredClient = clientAuthenticationToken.getRegisteredClient();

        if (registeredClient == null || !registeredClient.getAuthorizationGrantTypes().contains(AUTHORIZATION_GRANT_TYPE_PASSWORD)) {
            throw new OAuth2AuthorizationServerException("AUTH_SERVER_ERROR_0", messageSource.getMessage("AUTH_SERVER_ERROR_0", null, getLocale()));
        }

        String username = passwordAuthentication.getUsername();
        String password = passwordAuthentication.getPassword();

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_1",
                    messageSource.getMessage("AUTH_SERVER_ERROR_1", null, getLocale())
            );
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new OAuth2AuthorizationServerException(
                    "AUTH_SERVER_ERROR_5",
                    messageSource.getMessage("AUTH_SERVER_ERROR_5", null, getLocale())
            );
        }

        ((OAuth2ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
                .setDetails(userDetails);

        DefaultOAuth2TokenContext.Builder defaultTokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientAuthenticationToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(registeredClient.getScopes())
                .authorizationGrantType(AUTHORIZATION_GRANT_TYPE_PASSWORD)
                .authorizationGrant(passwordAuthentication);

        OAuth2TokenContext accessTokenContext = defaultTokenContextBuilder
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .build();

        OAuth2Token generatedAccessToken = customTokenGenerator.generate(accessTokenContext);
        if (generatedAccessToken == null) {
            throw new OAuth2AuthorizationServerException(
                    "",
                    messageSource.getMessage("", null, getLocale())
            );
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(),
                generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt()
        );

        DefaultOAuth2TokenContext refreshTokenContext = defaultTokenContextBuilder
                .tokenType(OAuth2TokenType.REFRESH_TOKEN)
                //.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        OAuth2Token generatedRefreshToken = customTokenGenerator.generate(refreshTokenContext);
        if (generatedRefreshToken == null) {
            throw new OAuth2AuthorizationServerException(
                    "",
                    messageSource.getMessage("", null, getLocale())
            );
        }

        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                generatedRefreshToken.getTokenValue(),
                generatedRefreshToken.getIssuedAt(),
                generatedRefreshToken.getExpiresAt()
        );

        OAuth2Authorization authorization = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(clientAuthenticationToken.getName())
                .authorizationGrantType(AUTHORIZATION_GRANT_TYPE_PASSWORD)
                .authorizedScopes(registeredClient.getScopes())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        authorizationService.save(authorization);

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientAuthenticationToken, accessToken, refreshToken, new HashMap<>(){{
            put("access_token_id", OAuth2Utils.extractTokenKey(accessToken.getTokenValue()));
            put("expirationDate", Objects.requireNonNull(accessToken.getExpiresAt()).atZone(ZoneId.systemDefault()).toString());
            put("userId", userDetails.getUserId());
        }});
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2GrantPasswordAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private OAuth2ClientAuthenticationToken getClientAuthenticationToken(OAuth2GrantPasswordAuthorizationToken passwordAuthentication) {
        OAuth2ClientAuthenticationToken authenticatedClient = null;

        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(passwordAuthentication.getPrincipal().getClass())) {
            authenticatedClient = (OAuth2ClientAuthenticationToken) passwordAuthentication.getPrincipal();
        }

        if (authenticatedClient != null && authenticatedClient.isAuthenticated()) {
            return authenticatedClient;
        }

        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

}
