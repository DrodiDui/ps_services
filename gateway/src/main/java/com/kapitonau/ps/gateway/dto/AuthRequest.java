package com.kapitonau.ps.gateway.dto;

import java.time.ZonedDateTime;

public record AuthRequest(
        String access_token,
        String refresh_token,
        String access_token_id,
        String token_type,
        String expires_in,
        ZonedDateTime expirationDate,
        Long userId
) {
}
