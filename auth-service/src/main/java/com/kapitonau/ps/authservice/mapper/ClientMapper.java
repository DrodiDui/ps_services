package com.kapitonau.ps.authservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapitonau.ps.authservice.model.ClientModel;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private final ObjectMapper objectMapper;

    public ClientMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    public ClientModel toClientModel(RegisteredClient registeredClient) {
        return ClientModel.builder()
                .id(Long.valueOf(registeredClient.getId()))
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientSecret(registeredClient.getClientSecret())
                .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(getAuthenticationMethodString(registeredClient))
                .authorizationGrantTypes(getAuthorizationGrantTypeString(registeredClient))
                .scopes(getScopeString(registeredClient))
                .clientSettings(writeClientSettingToMap(registeredClient))
                .tokenSettings(writeTokenSettingToMap(registeredClient))
                .build();
    }

    public RegisteredClient toRegisteredClient(ClientModel model) {

        ClientSettings clientSettings = model.getClientSettings().isEmpty() ? ClientSettings.builder().build() : ClientSettings.withSettings(model.getClientSettings()).build();

        TokenSettings tokenSettings = buildTokenSettings(model);

        return RegisteredClient.withId(model.getId().toString())
                .clientId(model.getClientId())
                .clientIdIssuedAt(model.getClientIdIssuedAt())
                .clientSecret(model.getClientSecret())
                .clientSecretExpiresAt(model.getClientSecretExpiresAt())
                .clientName(model.getClientName())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(
                        Arrays.stream(model.getClientAuthenticationMethods().split(" "))
                                .map(ClientAuthenticationMethod::new).collect(Collectors.toSet())))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(
                        Arrays.stream(model.getAuthorizationGrantTypes().split(" "))
                                .map(AuthorizationGrantType::new).collect(Collectors.toSet())))
                .scopes(strings -> strings.addAll(
                        Arrays.stream(model.getScopes().split(" ")).collect(Collectors.toSet())
                ))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }


    private String getScopeString(RegisteredClient registeredClient) {
        return registeredClient.getScopes()
                .stream()
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
    }

    private String getAuthorizationGrantTypeString(RegisteredClient registeredClient) {
        return registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(AuthorizationGrantType::getValue)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
    }

    private String getAuthenticationMethodString(RegisteredClient registeredClient) {
        return registeredClient.getClientAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethod::getValue)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
    }

    private static TokenSettings buildTokenSettings(ClientModel model) {
        if (model.getTokenSettings().isEmpty()) {
            return TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.ofMinutes(10))
                    .build();
        } else {
            long accessTokenTtl = model.getTokenSettings().containsKey("access-token-time-to-live") ?
                    Long.parseLong(model.getTokenSettings().get("access-token-time-to-live").toString()) :
                    60L;
            long refreshTokenTtl = model.getTokenSettings().containsKey("refresh-token-time-to-live") ?
                    Long.parseLong(model.getTokenSettings().get("refresh-token-time-to-live").toString()) :
                    (60 * 24);
            return TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .accessTokenTimeToLive(Duration.ofMinutes(accessTokenTtl))
                    .refreshTokenTimeToLive(Duration.ofMinutes(refreshTokenTtl))
                    .build();
        }
    }

    private Map<String, Object> writeClientSettingToMap(RegisteredClient model) {
        try {
            if (model.getClientSettings() == null) {
                return new HashMap<>();
            }
            return objectMapper.convertValue(model.getClientSettings(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> writeTokenSettingToMap(RegisteredClient model) {
        try {
            if (model.getTokenSettings() == null) {
                return new HashMap<>();
            }
            return objectMapper.convertValue(model.getTokenSettings(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
