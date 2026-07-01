package dev.rivasjf.jessysecurity.account.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AccountResponseDto(
        String id,
        String title,
        String username,
        String description,
        String category,
        List<AccountAdditionalInformationResponseDto> additionalInformation
) {
}
