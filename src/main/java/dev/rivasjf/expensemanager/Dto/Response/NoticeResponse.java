package dev.rivasjf.expensemanager.Dto.Response;

import lombok.Builder;

@Builder
public record NoticeResponse(
        Long id,
        String message,
        String createdAt
) {
}
