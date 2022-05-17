package com.task.mx.domain.entity;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.AutoPopulated;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;


import java.util.Date;

@MappedEntity("users")
public record User(
   @Nullable @Id @AutoPopulated Long id,
   @Nullable @DateCreated Date dateCreated,
   @NonNull String username,
   @NonNull String password,
   @NonNull Boolean enabled) {
}
