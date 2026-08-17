package dev.rivasjf.jessysecurity.account.dto.request;

import dev.rivasjf.jessysecurity.account.entitie.CategoryAccount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AccountUpdateRequestDto (
        @NotBlank(message = "Id cannot be empty")
        String id,
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,
        @Size(min = 1, max = 100, message = "Username must be between 1 and 100 characters")
        String username,
        @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters")
        String description,
        CategoryAccount category,
        @Valid
        List<AccountAdditionalInformationUpdateRequestDto> additionalInformation
) {
    public AccountUpdateRequestDto {
        if (id != null) id = id.trim();
        if (title != null) title = title.trim();
        if (username != null) username = username.trim();
        if (description != null) description = description.trim();
    }
}
