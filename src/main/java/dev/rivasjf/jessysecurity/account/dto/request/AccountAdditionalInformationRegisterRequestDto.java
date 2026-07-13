package dev.rivasjf.jessysecurity.account.dto.request;

import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformationType;

public record AccountAdditionalInformationRegisterRequestDto (
        AdditionalInformationType type,
        String value,
        String key
) {
}
