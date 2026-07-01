package dev.rivasjf.jessysecurity.account.controller;

import dev.rivasjf.jessysecurity.account.dto.request.AccountRegisterRequestDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.service.AccountService;
import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ApiResponse<AccountResponseDto> registerAccount(
            @RequestBody AccountRegisterRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(this.accountService.registerAccount(userDetails.getUsername(), requestDto), null);
    }
}
