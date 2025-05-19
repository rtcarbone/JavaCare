package br.com.fiap.javacare.user.dto;

import br.com.fiap.javacare.user.model.UserType;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String fullName,
        String email,
        UserType type
) {
}
