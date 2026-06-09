package dev.rivasjf.expensemanager.auth.services;

import dev.rivasjf.expensemanager.Entities.User;
import dev.rivasjf.expensemanager.Repositories.UserRepository;
import dev.rivasjf.expensemanager.auth.dto.JwtResponse;
import dev.rivasjf.expensemanager.auth.dto.LoginRequestDto;
import dev.rivasjf.expensemanager.auth.dto.UserRegisterRequestDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public JwtResponse register(UserRegisterRequestDto request) {
        Boolean existEmailUser = userRepository.existsByEmail(request.email());
        if (existEmailUser) {
            throw new IllegalArgumentException("Email invalid");
        }
        User newUser = User.create(
                request.username(),
                request.email(),
                request.password()
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
        return null;
    }
}
