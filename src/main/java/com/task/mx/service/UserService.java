package com.task.mx.service;

import com.task.mx.domain.entity.User;
import io.micronaut.core.annotation.NonNull;

import java.util.List;

/**
 * Contract to perform Authentication operations
 */
public interface UserService {

    AddUserResult addUser(final User user);

    List<User> getAll();

    AddUserResult getUserByUsername(@NonNull final String username);

    @NonNull
    default AddUserResult addUserSuccessful(final @NonNull User saved) {
        return AddUserResult.builder()
                .success(true)
                .user(saved)
                .build();
    }

    @NonNull
    default AddUserResult getUserSucces(final @NonNull User get) {
        return AddUserResult.builder()
                .success(true)
                .user(get)
                .build();
    }

    @NonNull
    default AddUserResult duplicatedUserResult(final @NonNull User user) {
        return AddUserResult.builder()
                .success(false)
                .user(user)
                .errorMessage("The user with username '%s' already exists.".formatted(user.username()))
                .build();
    }

    @NonNull
    default AddUserResult dontExistUserResult(final @NonNull String username) {
        return AddUserResult.builder()
                .success(false)
                .user(null)
                .errorMessage("The user with username '%s' dont exists.".formatted(username))
                .build();
    }
}
