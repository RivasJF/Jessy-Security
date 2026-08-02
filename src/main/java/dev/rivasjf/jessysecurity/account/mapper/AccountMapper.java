package dev.rivasjf.jessysecurity.account.mapper;

import dev.rivasjf.jessysecurity.account.dto.response.AccountAdditionalInformationResponseDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountListResponseDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.entitie.Account;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformation;

import java.util.List;

public class AccountMapper {
    public static AccountResponseDto toDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getPublicId().toString())
                .title(account.getTitle())
                .username(account.getUsername())
                .description(account.getDescription())
                .category(account.getCategory().toString())
                .additionalInformation(toDto(account.getAdditionalInformation()))
                .build();
    }

    public static AccountAdditionalInformationResponseDto toDto(AdditionalInformation additionalInformation) {
        return AccountAdditionalInformationResponseDto.builder()
                .id(additionalInformation.getPublicId().toString())
                .type(additionalInformation.getType().toString())
                .value(additionalInformation.getValue())
                .key(additionalInformation.getKey())
                .build();
    }

    public static List<AccountAdditionalInformationResponseDto> toDto(List<AdditionalInformation> additionalInformation) {
        return additionalInformation.stream()
                .map(AccountMapper::toDto)
                .toList();
    }

    public static AccountListResponseDto toListDto(Account account) {
        return AccountListResponseDto.builder()
                .id(account.getPublicId().toString())
                .title(account.getTitle())
                .username(account.getUsername())
                .description(account.getDescription())
                .category(account.getCategory().toString())
                .build();
    }

}
