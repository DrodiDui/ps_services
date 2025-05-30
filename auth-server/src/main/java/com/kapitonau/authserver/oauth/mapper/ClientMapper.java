package com.kapitonau.authserver.oauth.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapitonau.authserver.oauth.model.RegisteredClientModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClientMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClientMapper() {
        ClassLoader classLoader = ObjectMapper.class.getClassLoader();
        List<Module> modules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper.registerModules(modules);
        this.objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    public RegisteredClientModel toClient(RegisteredClient client) {
        return RegisteredClientModel.builder()
                .id(Long.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientName(client.getClientName())
                .clientIdIssuedAt(client.getClientIdIssuedAt().atZone(ZoneId.systemDefault()))
                .clientSecretExpiresAt(client.getClientSecretExpiresAt().atZone(ZoneId.systemDefault()))
                .clientAuthenticationMethods(getAuthenticationMethodsString(client))
                .authorizationGrantTypes(getGrantTypesString(client))
                .scopes(client.getScopes().stream().reduce((s1, s2) -> s1 + " " + s2).orElse(""))
                .clientSettings(writeClientSettingToMap(client))
                .tokenSettings(writeTokenSettingToMap(client))
                .build();
    }

    public RegisteredClient toResponse(RegisteredClientModel model) {

        /*Map<String, Object> clientSettingsMap = stringToMap(model.getClientSettings());*/
        ClientSettings clientSettings = model.getClientSettings().isEmpty() ? ClientSettings.builder().build() : ClientSettings.withSettings(model.getClientSettings()).build();

        /*Map<String, Object> tokenSettingsMap = stringToMap(model.getTokenSettings());*/
        TokenSettings tokenSettings = buildTokenSettings(model);

        return RegisteredClient.withId(String.valueOf(model.getId()))
                .clientId(model.getClientId())
                .clientIdIssuedAt(model.getClientIdIssuedAt().toInstant())
                .clientSecret(model.getClientSecret())
                .clientName(model.getClientName())
                .clientIdIssuedAt(model.getClientIdIssuedAt().toInstant())
                .clientSecretExpiresAt(model.getClientSecretExpiresAt().toInstant())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(Arrays.stream(model.getClientAuthenticationMethods().split(" "))
                        .map(ClientAuthenticationMethod::new).toList()))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(
                        Arrays.stream(model.getAuthorizationGrantTypes().split(" "))
                                .map(AuthorizationGrantType::new).toList()))
                .scopes(strings -> strings.addAll(
                        Arrays.stream(model.getScopes().split(" ")).toList()
                ))
                .clientSettings(clientSettings)
                .tokenSettings(tokenSettings)
                .build();
    }

    @NotNull
    private static TokenSettings buildTokenSettings(RegisteredClientModel model) {
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

    private Map<String, Object> stringToMap(String data) {
        try {
            if (data == null || data.isEmpty()) {
                return new HashMap<>();
            }
            return objectMapper.readValue(data, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private String getGrantTypesString(RegisteredClient registeredClient) {
        return registeredClient.getAuthorizationGrantTypes()
                .stream()
                .map(AuthorizationGrantType::getValue)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
    }

    private String getAuthenticationMethodsString(RegisteredClient registeredClient) {
        return registeredClient.getClientAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethod::getValue)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
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

    private String clientSettingsToString(RegisteredClient registeredClient) {
        try {
            return registeredClient.getClientSettings() != null ?
                    objectMapper.writeValueAsString(registeredClient.getClientSettings()) :
                    "";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String tokenSettingsToString(RegisteredClient client) {
        try {
            return client.getTokenSettings() != null ?
                    objectMapper.writeValueAsString(client.getTokenSettings().getSettings()) :
                    "";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
