package br.com.fiap.javacare.notification.dto;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String fullName,
        String email,
        UserType type
) {
}
