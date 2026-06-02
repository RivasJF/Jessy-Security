package dev.rivasjf.expensemanager.Mapper;

import dev.rivasjf.expensemanager.Dto.Response.UserResponseDto;
import dev.rivasjf.expensemanager.Entities.User;

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
