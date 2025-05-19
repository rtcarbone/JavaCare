package br.com.fiap.javacare.user.graphql;

import br.com.fiap.javacare.user.dto.UserInputDTO;
import br.com.fiap.javacare.user.dto.UserResponseDTO;
import br.com.fiap.javacare.user.mapper.UserMapper;
import br.com.fiap.javacare.user.model.User;
import br.com.fiap.javacare.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;

@Controller
@RequiredArgsConstructor
public class UserMutationResolver {

    private final UserRepository repository;
    private final UserMapper mapper;

    @MutationMapping
    public UserResponseDTO createUser(@Argument UserInputDTO input) {
        repository.findByEmail(input.email())
                .ifPresent(u -> {
                    throw new ResponseStatusException(CONFLICT, "E-mail já cadastrado");
                });

        User user = mapper.toEntity(input);
        user.setId(UUID.randomUUID());

        return mapper.toDto(repository.save(user));
    }
}
