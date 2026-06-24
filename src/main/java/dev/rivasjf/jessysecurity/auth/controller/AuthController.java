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
    public ResponseEntity<ApiResponse<JwtResponseDto>> register(@Valid @RequestBody UserRegisterRequestDto request) {
        AuthenticationTokenDto jwtResponseDto = authService.register(request);
        ResponseCookie refreshTokenCOOKIE = this.createResponseCookie(jwtResponseDto.refresh_token());
        ApiResponse<JwtResponseDto> response = ApiResponse.success(JwtResponseDto.create(jwtResponseDto.access_token()),null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCOOKIE.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        AuthenticationTokenDto jwtResponseDto = this.authService.login(request);
        ResponseCookie refreshTokenCOOKIE = this.createResponseCookie(jwtResponseDto.refresh_token());
        ApiResponse<JwtResponseDto> response = ApiResponse.success(JwtResponseDto.create(jwtResponseDto.access_token()),null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCOOKIE.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Refresh token is missing", null));
        }
        AuthenticationTokenDto jwtResponseDto = this.authService.refresh(refreshToken);
        ApiResponse<JwtResponseDto> response = ApiResponse.success(JwtResponseDto.create(jwtResponseDto.access_token()),null);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/salt/{email}")
    public ApiResponse<SaltResponseDto> getSalt(@PathVariable String email) {
        return ApiResponse.success(this.authService.saltByEmail(email),null);
    }

    private @NonNull ResponseCookie createResponseCookie(String token) {
        return ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                //.secure(true) production with HTTPS
                .path("/api/auth/refresh") // solo la da para este path
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Strict") // contra CSRF
                .build();
    }

}
