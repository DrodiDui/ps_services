package com.kapitonau.authserver.exception.dto;

public record AuthorizationServerExceptionResponse(
        String devMessage,
        String userMessage
) {
}
