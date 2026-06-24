package dev.rivasjf.jessysecurity.auth.controller;

import dev.rivasjf.jessysecurity.auth.dto.SaltResponseDto;
import dev.rivasjf.jessysecurity.common.Dto.ApiResponse;
import dev.rivasjf.jessysecurity.auth.dto.JwtResponse;
import dev.rivasjf.jessysecurity.auth.dto.LoginRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.UserRegisterRequestDto;
import dev.rivasjf.jessysecurity.auth.services.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<JwtResponse>> register(@Valid @RequestBody UserRegisterRequestDto request) {
        JwtResponse jwtResponse = authService.register(request);
        ResponseCookie refreshTokenCOOKIE = ResponseCookie.from("refreshToken", jwtResponse.refresh_token())
                .httpOnly(true)
                //.secure(true) production with HTTPS
                .path("/api/auth/refresh") // solo la da para este path
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Strict") // contra CSRF
                .build();
        ApiResponse<JwtResponse> response = ApiResponse.success(jwtResponse,null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCOOKIE.toString())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequestDto request) {
        JwtResponse jwtResponse = this.authService.login(request);

        ResponseCookie refreshTokenCOOKIE = ResponseCookie.from("refreshToken", jwtResponse.refresh_token())
                .httpOnly(true)
                //.secure(true) production with HTTPS
                .path("/api/auth/refresh") // solo la da para este path
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .sameSite("Strict") // contra CSRF
                .build();

        ApiResponse<JwtResponse> response = ApiResponse.success(jwtResponse,null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCOOKIE.toString())
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Refresh token is missing", null));
        }
        ApiResponse<JwtResponse> response = ApiResponse.success(this.authService.refresh(refreshToken),null);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/salt/{email}")
    public ApiResponse<SaltResponseDto> getSalt(@PathVariable String email) {
        return ApiResponse.success(this.authService.saltByEmail(email),null);
    }

}
