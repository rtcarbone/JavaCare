package br.com.fiap.javacare.notification.dto;

import br.com.fiap.javacare.notification.model.UserType;

import java.io.Serializable;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String fullName,
        String email,
        UserType type
) implements Serializable {
}
