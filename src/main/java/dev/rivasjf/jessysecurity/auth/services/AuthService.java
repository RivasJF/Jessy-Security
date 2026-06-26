package dev.rivasjf.jessysecurity.auth.services;

import dev.rivasjf.jessysecurity.auth.dto.*;
import dev.rivasjf.jessysecurity.auth.dto.request.LoginRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.request.UserRegisterRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.response.SaltResponseDto;
import dev.rivasjf.jessysecurity.user.entity.User;
import dev.rivasjf.jessysecurity.user.repository.UserRepository;
import dev.rivasjf.jessysecurity.utils.ValidateEmail;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationTokenDto register(UserRegisterRequestDto request) {
        Boolean existEmailUser = userRepository.existsByEmail(request.email());
        if (existEmailUser) {
            throw new IllegalArgumentException("Email invalid");
        }
        String encodedPassword = passwordEncoder.encode(request.publicKey());
        User newUser = User.create(
                request.username(),
                request.email(),
                encodedPassword,
                request.publicSalt()
        );
        User saveUser = this.userRepository.save(newUser);
        String token = jwtService.generateToken(saveUser);
        String refreshToken = jwtService.refreshToken(saveUser);
        return AuthenticationTokenDto.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .build();
    }

    public AuthenticationTokenDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.publicKey()
                )
        );
        User user = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.refreshToken(user);
        return AuthenticationTokenDto.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .build();
    }

    public AuthenticationTokenDto refresh(String token) {
        String userEmail = this.jwtService.extractUsername(token);

        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!this.jwtService.isTokenValid(token, user)) {
            throw new IllegalArgumentException("Token invalid");
        }

        String accessToken = jwtService.generateToken(user);
        return AuthenticationTokenDto.builder()
                .access_token(accessToken)
                .refresh_token(token)
                .build();
    }

    public SaltResponseDto saltByEmail(String email) {
        String userEmail = ValidateEmail.validateEmail(email);
        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        return SaltResponseDto.builder()
                .email(user.getEmail())
                .salt(user.getPublicSalt())
                .build();
    }
}
