package com.kapitonau.gatewayservice.dto;

import java.time.ZonedDateTime;

public record AuthResponse(
        String tokenId,
        String refreshToken,
        String tokenExpirationDate
) {
}
