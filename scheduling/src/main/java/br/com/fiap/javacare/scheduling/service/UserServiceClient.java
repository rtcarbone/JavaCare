package br.com.fiap.javacare.scheduling.service;

import br.com.fiap.javacare.scheduling.model.ActionType;
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

    public boolean canAccess(UUID requesterId, UUID targetUserId, ActionType actionType) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(userServiceUrl)
                .path("/users/")
                .path(requesterId.toString())
                .path("/can-access")
                .queryParam("action", actionType.name());

        if (targetUserId != null) {
            builder.queryParam("targetUserId", targetUserId.toString());
        }

        String url = builder.toUriString();

        return Boolean.TRUE.equals(
                restTemplate.getForObject(url, Boolean.class)
        );
    }

}
