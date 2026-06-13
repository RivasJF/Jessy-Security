package dev.rivasjf.expensemanager.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponseDto (
        String id,
        String username,
        String email,
        String createdAt
) {
}
