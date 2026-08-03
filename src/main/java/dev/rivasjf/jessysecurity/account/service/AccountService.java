package dev.rivasjf.jessysecurity.account.service;

import dev.rivasjf.jessysecurity.account.dto.AccountAdditionalInformationUpdateDto;
import dev.rivasjf.jessysecurity.account.dto.AccountUpdateDto;
import dev.rivasjf.jessysecurity.account.dto.request.AccountAdditionalInformationUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.dto.request.AccountRegisterRequestDto;
import dev.rivasjf.jessysecurity.account.dto.request.AccountUpdateRequestDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountListResponseDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.entitie.Account;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformation;
import dev.rivasjf.jessysecurity.account.mapper.AccountMapper;
import dev.rivasjf.jessysecurity.account.repository.AccountRepository;
import dev.rivasjf.jessysecurity.user.entity.User;
import dev.rivasjf.jessysecurity.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public AccountResponseDto registerAccount(String userEmail, AccountRegisterRequestDto requestDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Account account = Account.create(
                user,
                requestDto.title(),
                requestDto.username(),
                requestDto.description(),
                requestDto.category()
        );
        List<AdditionalInformation> additionalInformationsList = requestDto.additionalInformation().stream()
                .map(info -> AdditionalInformation.create(account, info.type(), info.value(), info.key()))
                .toList();
        account.addAdditionalInformation(additionalInformationsList);

        Account saveAccount = accountRepository.save(account);
        return AccountMapper.toDto(saveAccount);
    }

    public List<AccountListResponseDto> getUserAccounts(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Account> accounts = accountRepository.findAllByUserId(user.getId());
        return accounts.stream().map(AccountMapper::toListDto).toList();
    }

    public AccountResponseDto getAccount(String userEmail, String id) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        Account account = accountRepository.findByUserAndPublicId(user, UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        return AccountMapper.toDto(account);
    }

    public AccountResponseDto updateAccount(String userEmail, AccountUpdateRequestDto requestDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Account account = accountRepository.findByUserAndPublicId(user, UUID.fromString(requestDto.id()))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        var updatedAccount = AccountUpdateDto.builder()
                .title(requestDto.title())
                .username(requestDto.username())
                .description(requestDto.description())
                .category(requestDto.category())
                .additionalInformation(AccountMapper.toListDto(requestDto.additionalInformation()))
                .build();
        account.updateAccount(updatedAccount);
        Account saveAccount = accountRepository.save(account);
        return AccountMapper.toDto(saveAccount);
    }

    public void deleteAccount(UUID id) {
    }


}
