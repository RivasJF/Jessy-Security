package dev.rivasjf.jessysecurity.auth.dto;

import lombok.Builder;

@Builder
public record AuthenticationTokenDto (
        String access_token,
        String refresh_token
) {
}
