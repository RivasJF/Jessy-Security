package dev.rivasjf.digitalbrain.Dto.Response;

import lombok.Builder;

@Builder
public record NoticeResponse(
        Long id,
        String message,
        String createdAt
) {
}
