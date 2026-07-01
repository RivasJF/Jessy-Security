package dev.rivasjf.jessysecurity.account.service;

import dev.rivasjf.jessysecurity.account.dto.request.AccountRegisterRequestDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.entitie.Account;
import dev.rivasjf.jessysecurity.account.entitie.AdditionalInformation;
import dev.rivasjf.jessysecurity.account.mapper.AccountMapper;
import dev.rivasjf.jessysecurity.account.repository.AccountRepository;
import dev.rivasjf.jessysecurity.user.entity.User;
import dev.rivasjf.jessysecurity.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                .map(info -> AdditionalInformation.create(account, info.type(), info.value()))
                .toList();
        account.addAdditionalInformation(additionalInformationsList);

        Account saveAccount = accountRepository.save(account);
        return AccountMapper.toDto(saveAccount);
    }

    public AccountResponseDto getAccount(UUID id) {
        return null;
    }

    public void deleteAccount(UUID id) {
    }
}
