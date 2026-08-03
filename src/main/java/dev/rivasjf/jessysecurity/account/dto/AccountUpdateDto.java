package dev.rivasjf.jessysecurity.account.dto;

import dev.rivasjf.jessysecurity.account.entitie.CategoryAccount;
import lombok.Builder;

import java.util.List;

@Builder
public record AccountUpdateDto (
        String title,
        String username,
        String description,
        CategoryAccount category,
        List<AccountAdditionalInformationUpdateDto> additionalInformation
){
}
