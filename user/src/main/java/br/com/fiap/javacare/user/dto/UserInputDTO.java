package br.com.fiap.javacare.user.dto;

import br.com.fiap.javacare.user.model.UserType;

import java.io.Serializable;

public record UserInputDTO(
        String fullName,
        String email,
        UserType type
) implements Serializable {
}
