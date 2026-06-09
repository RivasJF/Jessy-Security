package dev.rivasjf.expensemanager.auth.controller;

import dev.rivasjf.expensemanager.Common.Dto.ApiResponse;
import dev.rivasjf.expensemanager.auth.dto.JwtResponse;
import dev.rivasjf.expensemanager.auth.dto.LoginRequestDto;
import dev.rivasjf.expensemanager.auth.dto.UserRegisterRequestDto;
import dev.rivasjf.expensemanager.auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<JwtResponse> register(@Valid @RequestBody UserRegisterRequestDto request) {
        return ApiResponse.success(this.authService.register(request),null);
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@Valid @RequestBody LoginRequestDto request) {
        return ApiResponse.success(this.authService.login(request),null);
    }

}
