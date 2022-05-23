package com.task.mx.service.impl;

import com.task.mx.domain.entity.RefreshTokenEntity;
import com.task.mx.infrastructure.repository.RefreshTokenRepository;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.errors.OauthErrorResponseException;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT;

@AllArgsConstructor
@Slf4j
@Singleton
public class CustomRefreshTokenPersistence implements RefreshTokenPersistence {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void persistToken(RefreshTokenGeneratedEvent event) {
        Objects.requireNonNull(event, "Event is null");
        var payload = event.getRefreshToken();
        refreshTokenRepository.save(event.getAuthentication().getName(), payload, false, Instant.now());
    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        return Flux.create(emitter -> {
            Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken);
            if (tokenOpt.isPresent()) {
                RefreshTokenEntity token = tokenOpt.get();
                if (token.revoked()) {
                    log.warn("Token {}" , token.refreshToken() ,"is revoked");
                    emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null));
                } else {
                    emitter.next(Authentication.build(token.username()));
                    emitter.complete();
                }
            } else {
                emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null));
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}