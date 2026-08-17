package dev.rivasjf.jessysecurity.account.dto.request;

import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformationType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountAdditionalInformationUpdateRequestDto(
        String id,
        @AssertTrue(message = "Deleted must be true")
        Boolean deleted,
        AdditionalInformationType type,
        String value,
        String key
){
    public AccountAdditionalInformationUpdateRequestDto {
        if(id != null) id = id.trim();
        if (value != null) value = value.trim();
        if (key != null) key = key.trim();
    }
}
