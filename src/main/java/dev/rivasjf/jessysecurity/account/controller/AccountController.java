package dev.rivasjf.jessysecurity.account.controller;

import dev.rivasjf.jessysecurity.account.dto.request.AccountRegisterRequestDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountListResponseDto;
import dev.rivasjf.jessysecurity.account.dto.response.AccountResponseDto;
import dev.rivasjf.jessysecurity.account.service.AccountService;
import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController (AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountResponseDto> registerAccount(
            @RequestBody AccountRegisterRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.CREATED, this.accountService.registerAccount(userDetails.getUsername(), requestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountListResponseDto>> getUserAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(HttpStatus.OK, this.accountService.getUserAccounts(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        return ApiResponse.success(HttpStatus.OK, this.accountService.getAccount(userDetails.getUsername(), id));
    }
}
