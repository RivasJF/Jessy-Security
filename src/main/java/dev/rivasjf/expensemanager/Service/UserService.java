package dev.rivasjf.expensemanager.Service;

import dev.rivasjf.expensemanager.Dto.Request.UserRegisterRequestDto;
import dev.rivasjf.expensemanager.Dto.Response.UserResponseDto;
import dev.rivasjf.expensemanager.Entities.User;
import dev.rivasjf.expensemanager.Mapper.UserMapper;
import dev.rivasjf.expensemanager.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto registerUser(UserRegisterRequestDto request) {
        Boolean existEmailUser = userRepository.existsByEmail(request.email());
        if (existEmailUser) {
            throw new IllegalArgumentException("Email invalid");
        }
        User newUser = User.create(
                request.username(),
                request.email(),
                request.password()
        );

        this.userRepository.save(newUser);
        return UserMapper.toDto(newUser);
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    public UserResponseDto getUserById(String id) {
        User user = this.userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

}
