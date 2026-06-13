package dev.rivasjf.jessysecurity.auth.dto;

import lombok.Builder;

@Builder
public record JwtResponse(
        String access_token,
        String refresh_token
) {
}
