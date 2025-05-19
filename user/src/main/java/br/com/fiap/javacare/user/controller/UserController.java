package br.com.fiap.javacare.user.controller;

import br.com.fiap.javacare.user.dto.UserResponseDTO;
import br.com.fiap.javacare.user.exception.UserNotFoundException;
import br.com.fiap.javacare.user.mapper.UserMapper;
import br.com.fiap.javacare.user.model.ActionType;
import br.com.fiap.javacare.user.model.User;
import br.com.fiap.javacare.user.model.UserType;
import br.com.fiap.javacare.user.repository.UserRepository;
import br.com.fiap.javacare.user.util.AccessControlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final UserMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(repository.findById(id)
                                         .map(mapper::toDto)
                                         .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    @GetMapping("/{id}/can-access")
    public ResponseEntity<Boolean> canAccess(
            @PathVariable UUID id,
            @RequestParam(required = false) UUID targetUserId,
            @RequestParam ActionType action
    ) {
        User requester = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Requester not found"));

        boolean result = switch (action) {
            case VIEW_HISTORY -> AccessControlUtil.canViewHistory(requester, targetUserId);
            case EDIT_HISTORY -> requester.getType() == UserType.MEDIC;
            case REGISTER_CONSULTATION ->
                    requester.getType() == UserType.MEDIC || requester.getType() == UserType.NURSE;
        };

        return ResponseEntity.ok(result);
    }

}
