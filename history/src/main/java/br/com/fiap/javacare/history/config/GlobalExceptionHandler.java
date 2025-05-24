package br.com.fiap.javacare.history.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

@Component
@Slf4j
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        log.error("Exception handled: {}", ex.getMessage(), ex);

        return switch (ex) {
            case EntityNotFoundException entityNotFoundException -> toGraphQLError("Not Found", ex, env);
            case AccessDeniedException accessDeniedException -> toGraphQLError("Access Denied", ex, env);
            case ResponseStatusException responseStatusException ->
                    toGraphQLError("Error: " + responseStatusException.getReason(), ex, env);
            default -> toGraphQLError("Internal Server Error", ex, env);
        };

    }

    private GraphQLError toGraphQLError(String message, Throwable ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError()
                .message(message)
                .path(env.getExecutionStepInfo()
                              .getPath())
                .location(env.getField()
                                  .getSourceLocation())
                .build();
    }

}
