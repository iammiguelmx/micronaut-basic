package com.task.mx.service.impl;

import com.task.mx.domain.entity.User;
import com.task.mx.infrastructure.repository.UserRepository;
import com.task.mx.service.UserService;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;


@Singleton
@RequiredArgsConstructor
public class UserImpl implements UserService {

    @NonNull
    private final UserRepository userRepository;

    @Override
    public User addUser(final User user) {
        return userRepository.save(user);
    }
}
