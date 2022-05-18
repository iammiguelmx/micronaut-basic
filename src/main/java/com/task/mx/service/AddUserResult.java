package com.task.mx.service;

import com.task.mx.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

/**
 * Represents the dara
 */
@Builder
@Getter
public class AddUserResult {
    @Builder.Default
    private final boolean success = false;
    private final User user;
    private final String errorMessage;
    private final Optional<Throwable> errorCause;
}