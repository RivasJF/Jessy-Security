package dev.rivasjf.expensemanager.Dto.Response;

import lombok.Builder;

@Builder
public record UserResponseDto (
        String id,
        String username,
        String email,
        String createdAt
) {
}
