package com.task.mx.service.impl;

import com.task.mx.domain.entity.User;
import com.task.mx.infrastructure.repository.UserRepository;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Optional;

import static io.micronaut.security.authentication.AuthenticationFailureReason.USER_NOT_FOUND;


@Singleton
@AllArgsConstructor
@Slf4j
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    private final UserRepository repository;

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        final String username = authenticationRequest.getIdentity().toString();
        final String password = authenticationRequest.getSecret().toString();
        Optional<User> existingUser = repository.findByUsername(username);

        return Flux.create(emitter -> {
            if (existingUser.isPresent()) {
                if (username.equals(existingUser.get().username()) && password.equals(existingUser.get().password())) {
                    emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity()));
                    emitter.complete();
                } else {
                    emitter.error(AuthenticationResponse.exception());
                }
            }else {
                emitter.next(new AuthenticationFailed(USER_NOT_FOUND));
                emitter.complete();
            }
          }, FluxSink.OverflowStrategy.ERROR);
    }

}


