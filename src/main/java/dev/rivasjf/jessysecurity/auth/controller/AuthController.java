package dev.rivasjf.jessysecurity.auth.controller;

import dev.rivasjf.jessysecurity.auth.dto.*;
import dev.rivasjf.jessysecurity.auth.dto.request.LoginRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.request.UserRegisterRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.response.JwtResponseDto;
import dev.rivasjf.jessysecurity.auth.dto.response.SaltResponseDto;
import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import dev.rivasjf.jessysecurity.auth.services.AuthService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@Valid @RequestBody UserRegisterRequestDto request) {
        AuthenticationTokenDto jwtResponseDto = authService.register(request);
        ResponseCookie refreshTokenCOOKIE = this.authService.createResponseCookie(jwtResponseDto.refresh_token());
        JwtResponseDto response = JwtResponseDto.create(jwtResponseDto.access_token());
        return ApiResponse.success(
                HttpStatus.CREATED,
                response,
                HttpHeaders.SET_COOKIE,
                refreshTokenCOOKIE.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthenticationTokenDto jwtResponseDto = this.authService.login(request);
        ResponseCookie refreshTokenCOOKIE = this.authService.createResponseCookie(jwtResponseDto.refresh_token());
        JwtResponseDto response = JwtResponseDto.create(jwtResponseDto.access_token());
        return ApiResponse.success(
                HttpStatus.OK,
                response,
                HttpHeaders.SET_COOKIE,
                refreshTokenCOOKIE.toString());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ApiResponse.error(
                    HttpStatus.BAD_REQUEST,
                    "Refresh token is missing",
                    null);
        }
        AuthenticationTokenDto jwtResponseDto = this.authService.refresh(refreshToken);
        JwtResponseDto response = JwtResponseDto.create(jwtResponseDto.access_token());
        return ApiResponse.success(
                HttpStatus.OK,
                response);
    }

    @GetMapping("/salt/{email}")
    public ResponseEntity<SaltResponseDto> getSalt(@PathVariable String email) {
        return ApiResponse.success(HttpStatus.OK, this.authService.saltByEmail(email));
    }

}
