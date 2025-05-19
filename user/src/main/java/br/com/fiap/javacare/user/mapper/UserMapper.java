package br.com.fiap.javacare.user.mapper;

import br.com.fiap.javacare.user.dto.UserInputDTO;
import br.com.fiap.javacare.user.dto.UserResponseDTO;
import br.com.fiap.javacare.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserInputDTO dto);

    UserResponseDTO toDto(User user);
}