package dev.rivasjf.jessysecurity.auth.dto.request;

public record LoginRequestDto (
        String email,
        String publicKey
){
}
