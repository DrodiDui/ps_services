package com.kapitonau.ps.gateway.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kapitonau.ps.gateway.dto.AuthRequest;
import com.kapitonau.ps.gateway.dto.AuthResponse;
import com.kapitonau.ps.gateway.service.AuthTokenStore;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
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
            mapper.registerModule(new JavaTimeModule());
            AuthRequest authRequest = mapper.readValue(bytes, AuthRequest.class);
            authTokenStore.storeToken(authRequest.access_token_id(), authRequest.access_token());
            AuthResponse authResponse = new AuthResponse(
                    authRequest.access_token_id(),
                    authRequest.refresh_token(),
                    authRequest.expirationDate(),
                    authRequest.userId()
            );
            return Mono.just(authResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}