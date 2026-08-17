package dev.rivasjf.jessysecurity.auth.dto.response;

import lombok.Builder;

@Builder
public record SaltResponseDto(
        String email,
        String salt
) {
}
