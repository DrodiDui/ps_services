package com.kapitonau.ps.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationServerExceptionResponse {

    private  String devMessage;
    private String userMessage;

}
