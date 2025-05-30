package com.kapitonau.gatewayservice.store;

import com.kapitonau.gatewayservice.dto.AuthRequest;
import com.kapitonau.gatewayservice.dto.AuthResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthTokenStore {

    private static final Map<String, String> authCache = new HashMap<String, String>();

    public void storeAuth(AuthRequest authRequest) {
        authCache.put(authRequest.access_token_id(), authRequest.access_token());
    }

    public String getAuthToken(String tokenId) {
        return authCache.getOrDefault(tokenId, "ERROR");
    }

}
