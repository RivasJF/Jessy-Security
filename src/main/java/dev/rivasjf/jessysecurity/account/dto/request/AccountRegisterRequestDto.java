package dev.rivasjf.jessysecurity.account.dto.request;

import dev.rivasjf.jessysecurity.account.entitie.CategoryAccount;

import java.util.List;

public record AccountRegisterRequestDto (
        String title,
        String username,
        String description,
        CategoryAccount category,
        List<AccountAdditionalInformationRegisterRequestDto> additionalInformation
) {

}
