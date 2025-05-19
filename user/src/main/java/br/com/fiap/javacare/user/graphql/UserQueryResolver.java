package br.com.fiap.javacare.user.graphql;

import br.com.fiap.javacare.user.dto.UserResponseDTO;
import br.com.fiap.javacare.user.exception.UserNotFoundException;
import br.com.fiap.javacare.user.mapper.UserMapper;
import br.com.fiap.javacare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    private final UserRepository repository;
    private final UserMapper mapper;

    @QueryMapping
    public List<UserResponseDTO> users() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @QueryMapping
    public UserResponseDTO userById(@Argument UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @QueryMapping
    public UserResponseDTO userByEmail(@Argument String email) {
        return repository.findByEmail(email)
                .map(mapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
