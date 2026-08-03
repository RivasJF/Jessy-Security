package dev.rivasjf.jessysecurity.account.dto;

import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformationType;
import lombok.Builder;

@Builder
public record AccountAdditionalInformationUpdateDto (
        String id,
        Boolean deleted,
        AdditionalInformationType type,
        String value,
        String key
) {
}
