package com.kapitonau.ps.gateway.filter;

import com.kapitonau.ps.gateway.service.AuthTokenStore;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class RewriteAuthorizationTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<RewriteAuthorizationTokenGatewayFilterFactory.Config> {

    private final AuthTokenStore authTokenStore;

    public RewriteAuthorizationTokenGatewayFilterFactory(AuthTokenStore authTokenStore) {
        super(Config.class);
        this.authTokenStore = authTokenStore;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authTokenValue = null;
            if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                List<String> headerValues = headers.get(HttpHeaders.AUTHORIZATION);
                for (String headerValue : headerValues) {
                    if (headerValue.startsWith("Bearer ")) {
                        String tokenId = headerValue.split(" ")[1];
                        authTokenValue = authTokenStore.getAccessToken(tokenId);
                    }
                }
            }
            ServerWebExchange mutatedExchange;
            if (authTokenValue != null && !authTokenValue.equals("ERROR")) {
                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authTokenValue)
                        .build();

                mutatedExchange = exchange.mutate().request(request).build();
            } else {
                mutatedExchange = exchange;
            }
            return chain.filter(mutatedExchange);
        };
    }

    public static class Config {

    }
}
