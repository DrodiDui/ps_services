package com.kapitonau.ps.authservice.oauth;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class OAuth2GrantPasswordAuthorizationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private static final AuthorizationGrantType AUTHORIZATION_GRANT_TYPE = AuthorizationGrantTypePassword.PASSWORD;
    private final String username;
    private final String password;
    private final Set<String> scopes;


    public OAuth2GrantPasswordAuthorizationToken(
            Authentication clientPrincipal, Map<String, Object> additionalParameters, String username, String password, Set<String> scopes
    ) {
        super(AUTHORIZATION_GRANT_TYPE, clientPrincipal, additionalParameters);
        this.username = username;
        this.password = password;
        this.scopes = Collections.unmodifiableSet(
                scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }
}
