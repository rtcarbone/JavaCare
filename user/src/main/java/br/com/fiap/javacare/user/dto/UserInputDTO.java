package br.com.fiap.javacare.user.dto;

import br.com.fiap.javacare.user.model.UserType;

public record UserInputDTO(
        String fullName,
        String email,
        UserType type
) {
}
