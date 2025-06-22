package com.kapitonau.ps.gateway.dto;

import java.time.ZonedDateTime;

public class AuthResponse {

    private String tokenId;
    private String refreshToken;
    private ZonedDateTime expiresAt;
    private Long userId;

    public AuthResponse(String tokenId, String refreshToken, ZonedDateTime expiresAt, Long userId) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
