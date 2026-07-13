package dev.rivasjf.jessysecurity.user.mapper;

import dev.rivasjf.jessysecurity.user.dto.response.UserResponseDto;
import dev.rivasjf.jessysecurity.user.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getPublicId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt().toString()
        );
    }

}
