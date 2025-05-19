package br.com.fiap.javacare.user.config;

import br.com.fiap.javacare.user.exception.UserNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
@Slf4j
public class GlobalExceptionHandler extends DataFetcherExceptionResolverAdapter {

    private static final List<Class<? extends Throwable>> notFoundExceptions = List.of(
            EntityNotFoundException.class,
            UserNotFoundException.class
    );

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        log.error("Exception handled: {}", ex.getMessage(), ex);

        if (notFoundExceptions.stream()
                .anyMatch(e -> e.isInstance(ex))) {
            return toGraphQLError("Not Found", ex, env);
        }

        if (ex instanceof AccessDeniedException) {
            return toGraphQLError("Access Denied", ex, env);
        }

        if (ex instanceof ResponseStatusException) {
            return toGraphQLError("Error: " + ((ResponseStatusException) ex).getReason(), ex, env);
        }

        return toGraphQLError("Internal Server Error", ex, env);
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
