package com.task.mx.service;

import com.task.mx.domain.entity.User;

/**
 * Contract to perform Authentication operations
 */
public interface UserService {

    User addUser(final User user);
}
