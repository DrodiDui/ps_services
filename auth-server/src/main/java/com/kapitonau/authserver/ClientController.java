package com.kapitonau.authserver;

import com.kapitonau.authserver.oauth.repository.custom.CustomRegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final CustomRegisteredClientRepository clientRepository;

    @GetMapping("/save")
    public String saveClient() {
        RegisteredClient demoClient = RegisteredClient.withId(String.valueOf(100001))
                .clientName("Demo client")
                .clientId("demo-client-1")
                .clientSecret("{noop}demo-secret")
                .redirectUri("http://localhost:8080/auth")
                .clientIdIssuedAt(Instant.now())
                .clientSecretExpiresAt(Instant.now().plus(Duration.ofDays(1)))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .clientSettings(null)
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofMinutes(300))
                                .refreshTokenTimeToLive(Duration.ofMinutes(600))
                                .authorizationCodeTimeToLive(Duration.ofMinutes(20))
                                .reuseRefreshTokens(false)
                                .build()
                )
                .build();
        clientRepository.save(demoClient);
        return "done";
    }
}
