package com.task.mx.service.impl;

import com.task.mx.domain.entity.User;
import com.task.mx.infrastructure.repository.UserRepository;
import com.task.mx.service.AddUserResult;
import com.task.mx.service.UserService;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Singleton
@RequiredArgsConstructor
public class UserImpl implements UserService {

    @NonNull
    private final UserRepository userRepository;

    @Override
    public AddUserResult addUser(final User user) {
        return userRepository.findByUsername(user.username())
                .map(this::handleExistingUser)
                .orElseGet(() -> internalAddUser(user));
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public AddUserResult getUserByUsername(String username) {
        log.info("Trying get User by username {}", username);
        return userRepository.findByUsername(username)
                .map(this::getUserSucces)
                .orElseGet(()-> handleDontExistingUser(username));
    }

    @NonNull
    private AddUserResult internalAddUser(final @NonNull User user) {
        log.info("Trying to add user {}", user.username());
        final var saved = userRepository.save(user);
        log.info("Added user with ID: {}", saved.id());

        return addUserSuccessful(saved);
    }


    @NonNull
    private AddUserResult handleDontExistingUser(final @NonNull String username) {
        log.warn("The username '{}' dont exists.", username);
        return dontExistUserResult(username);
    }


    @NonNull
    private AddUserResult handleExistingUser(final @NonNull User existingUser) {
        log.warn("The username '{}' already exists.", existingUser.username());
        return duplicatedUserResult(existingUser);
    }
}
