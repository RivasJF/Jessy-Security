package dev.rivasjf.expensemanager.user.service;

import dev.rivasjf.expensemanager.user.dto.response.UserResponseDto;
import dev.rivasjf.expensemanager.user.entity.User;
import dev.rivasjf.expensemanager.user.mapper.UserMapper;
import dev.rivasjf.expensemanager.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
