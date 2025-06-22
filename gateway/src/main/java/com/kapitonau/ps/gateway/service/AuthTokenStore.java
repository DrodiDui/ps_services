package com.kapitonau.ps.gateway.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthTokenStore {

    private Map<String, String> tokenStore = new HashMap<>();

    public void storeToken(String accessTokenId, String accessToken) {
        tokenStore.put(accessTokenId, accessToken);
    }

    public String getAccessToken(String accessTokenId) {
        return tokenStore.get(accessTokenId);
    }

    /*private static final String TOKEN_PREFIX = "token:";

    private final RedisTemplate<String, String> redisTemplate;

    public AuthTokenStore(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = "tokens", key = "#tokenId")
    public String getToken(String tokenId) {
        return redisTemplate.opsForValue().get(TOKEN_PREFIX + tokenId);
    }

    @CachePut(value = "tokens", key = "#tokenId")
    public void saveToken(String tokenId, String token, Duration ttl) {
        redisTemplate.opsForValue().set(
                TOKEN_PREFIX + tokenId,
                token,
                ttl
        );
    }

    @CacheEvict(value = "tokens", key = "#tokenId")
    public void deleteToken(String tokenId) {
        redisTemplate.delete(TOKEN_PREFIX + tokenId);
    }

    public boolean tokenExists(String tokenId) {
        Boolean exists = redisTemplate.hasKey(TOKEN_PREFIX + tokenId);
        return exists != null && exists;
    }*/
}
