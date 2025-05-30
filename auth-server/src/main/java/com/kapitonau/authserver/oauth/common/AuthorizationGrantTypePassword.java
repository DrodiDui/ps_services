package com.kapitonau.authserver.oauth.common;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class AuthorizationGrantTypePassword {
    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");
}
