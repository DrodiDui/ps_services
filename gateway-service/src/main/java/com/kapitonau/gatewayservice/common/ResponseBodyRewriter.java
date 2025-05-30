package com.kapitonau.gatewayservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapitonau.gatewayservice.dto.AuthRequest;
import com.kapitonau.gatewayservice.dto.AuthResponse;
import com.kapitonau.gatewayservice.store.AuthTokenStore;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Component
public class ResponseBodyRewriter implements RewriteFunction<byte[], AuthResponse> {

    private final AuthTokenStore authTokenStore;

    public ResponseBodyRewriter(AuthTokenStore authTokenStore) {
        this.authTokenStore = authTokenStore;
    }

    @Override
    public Publisher<AuthResponse> apply(ServerWebExchange serverWebExchange, byte[] bytes) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            ObjectMapper mapper = new ObjectMapper();
            AuthRequest authRequest = mapper.readValue(bytes, AuthRequest.class);
            authTokenStore.storeAuth(authRequest);
            AuthResponse authResponse = new AuthResponse(
                    authRequest.access_token_id(),
                    authRequest.refresh_token(),
                    authRequest.expirationDate()
            );
            return Mono.just(authResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
