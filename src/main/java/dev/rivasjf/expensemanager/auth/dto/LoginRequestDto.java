package dev.rivasjf.expensemanager.auth.dto;

public record LoginRequestDto (
        String email,
        String password
){
}
