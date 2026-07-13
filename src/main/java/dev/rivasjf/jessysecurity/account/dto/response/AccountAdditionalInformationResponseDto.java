package dev.rivasjf.jessysecurity.account.dto.response;

import lombok.Builder;

@Builder
public record AccountAdditionalInformationResponseDto(
        String type,
        String value,
        String key
) {
}
