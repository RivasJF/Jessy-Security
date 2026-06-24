package dev.rivasjf.jessysecurity.auth.dto.response;


public record JwtResponseDto(
        String access_token
) {

    public static JwtResponseDto create(String access_token) {
        return new JwtResponseDto(access_token);
    }
}
