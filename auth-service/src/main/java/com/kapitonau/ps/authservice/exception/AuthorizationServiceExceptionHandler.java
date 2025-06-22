package com.kapitonau.ps.authservice.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthorizationServiceExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(OAuth2AuthorizationServerException.class)
    public ResponseEntity<AuthorizationServerExceptionResponse> handleAuthorizationServerException(OAuth2AuthorizationServerException ex) {
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getHttpCode()))
                .body(ex.getExceptionResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthorizationServerExceptionResponse> handleUnexpectedException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new AuthorizationServerExceptionResponse(
                        "AUTH_SERVER_ERROR_500",
                        messageSource.getMessage("AUTH_SERVER_ERROR_500", null, getLocale())
                ));
    }
}
