package dev.rivasjf.jessysecurity.auth.services;

import dev.rivasjf.jessysecurity.user.entity.User;
import dev.rivasjf.jessysecurity.user.repository.UserRepository;
import dev.rivasjf.jessysecurity.auth.dto.JwtResponse;
import dev.rivasjf.jessysecurity.auth.dto.LoginRequestDto;
import dev.rivasjf.jessysecurity.auth.dto.UserRegisterRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
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

    public JwtResponse register(UserRegisterRequestDto request) {
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
        return JwtResponse.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .build();
    }

    public JwtResponse login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = this.userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.refreshToken(user);
        return JwtResponse.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .build();
    }

    public JwtResponse refresh(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Header invalid");
        }

        String token = header.substring(7);
        String userEmail = this.jwtService.extractUsername(token);

        User user = this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!this.jwtService.isTokenValid(token, user)) {
            throw new IllegalArgumentException("Token invalid");
        }

        String accessToken = jwtService.generateToken(user);
        return JwtResponse.builder()
                .access_token(accessToken)
                .refresh_token(token)
                .build();
    }
}
