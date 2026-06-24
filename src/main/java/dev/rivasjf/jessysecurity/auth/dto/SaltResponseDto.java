package dev.rivasjf.jessysecurity.auth.dto;

import lombok.Builder;

@Builder
public record SaltResponseDto(
        String email,
        String salt
) {
}
