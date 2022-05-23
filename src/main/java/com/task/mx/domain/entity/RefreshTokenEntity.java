package com.task.mx.domain.entity;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@MappedEntity("token")
public record RefreshTokenEntity (
        @Nullable @Id @AutoPopulated Long id,
        @Nullable  String  username ,
        @NonNull   String refreshToken,
        @NonNull   Boolean revoked,
        @DateCreated @NonNull @NotNull Instant dateCreated)
{ }