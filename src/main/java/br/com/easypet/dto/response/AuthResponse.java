package br.com.easypet.dto.response;

public record AuthResponse(

        String token,
        String name,
        String email,
        String role
) {
}
