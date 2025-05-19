package br.com.fiap.javacare.notification.service;

import br.com.fiap.javacare.notification.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${services.user.url}")
    private String userServiceUrl;

    public UserResponseDTO getUserById(UUID id) {
        String url = UriComponentsBuilder.fromHttpUrl(userServiceUrl)
                .path("/users/")
                .path(id.toString())
                .toUriString();

        return restTemplate.getForObject(url, UserResponseDTO.class);
    }

}
