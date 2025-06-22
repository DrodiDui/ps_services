package com.kapitonau.ps.authservice.oauth;

import com.kapitonau.ps.authservice.exception.OAuth2AuthorizationServerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

public class OAuth2GrantTypePasswordRequestConverter implements AuthenticationConverter {

    private final MessageSource messageSource;

    public OAuth2GrantTypePasswordRequestConverter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {

        if (!AuthorizationGrantTypePassword.PASSWORD.getValue().equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))) {
            return null;
        }

        MultiValueMap<String, String> parameters = getParameters(request);

        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
        if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
            throw new OAuth2AuthorizationServerException("AUTH_SERVER_VALIDATION_1", messageSource.getMessage("AUTH_SERVER_VALIDATION_1", null, getLocale()));
        }

        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
        if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
            throw new OAuth2AuthorizationServerException("AUTH_SERVER_VALIDATION_2", messageSource.getMessage("AUTH_SERVER_VALIDATION_2", null, getLocale()));
        }

        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope)
                && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1
        ) {
            throw new OAuth2AuthorizationServerException("AUTH_SERVER_VALIDATION_3", messageSource.getMessage("AUTH_SERVER_VALIDATION_3", null, getLocale()));
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        Map<String, Object> additionalParameters = parameters.entrySet().stream()
                .filter(entry ->
                        !OAuth2ParameterNames.GRANT_TYPE.equals(entry.getKey())
                                && !OAuth2ParameterNames.SCOPE.equals(entry.getKey())
                                && !OAuth2ParameterNames.PASSWORD.equals(entry.getKey())
                                && !OAuth2ParameterNames.USERNAME.equals(entry.getKey())
                )
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getFirst()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new OAuth2GrantPasswordAuthorizationToken(
                authentication, additionalParameters, username, password, requestedScopes
        );
    }

    private MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }
}