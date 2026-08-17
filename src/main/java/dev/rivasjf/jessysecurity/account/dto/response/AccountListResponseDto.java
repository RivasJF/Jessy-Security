package dev.rivasjf.jessysecurity.account.dto.response;

import lombok.Builder;

@Builder
public record AccountListResponseDto(
        String id,
        String title,
        String username,
        String description,
        String category
) {
}
