package com.kapitonau.authserver.exception;

import com.kapitonau.authserver.exception.dto.AuthorizationServerExceptionResponse;
import lombok.Getter;

@Getter
public class OAuth2AuthorizationServerException extends RuntimeException {

    private int httpCode = 400;
    private AuthorizationServerExceptionResponse exceptionResponse;

    public OAuth2AuthorizationServerException(int httpCode, AuthorizationServerExceptionResponse exceptionResponse) {
        this.httpCode = httpCode;
        this.exceptionResponse = exceptionResponse;
    }

    public OAuth2AuthorizationServerException(int httpCode, String devMessage, String userMessage) {
        this.httpCode = httpCode;
        this.exceptionResponse = new AuthorizationServerExceptionResponse(devMessage, userMessage);
    }

    public OAuth2AuthorizationServerException(String devMessage, String userMessage) {
        this.exceptionResponse = new AuthorizationServerExceptionResponse(devMessage, userMessage);
    }
}
