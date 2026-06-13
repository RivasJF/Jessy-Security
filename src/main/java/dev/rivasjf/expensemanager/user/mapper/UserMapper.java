package dev.rivasjf.expensemanager.user.mapper;

import dev.rivasjf.expensemanager.user.dto.response.UserResponseDto;
import dev.rivasjf.expensemanager.user.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt().toString()
        );
    }

}
