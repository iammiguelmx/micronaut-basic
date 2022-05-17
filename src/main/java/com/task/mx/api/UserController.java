package com.task.mx.api;

import com.task.mx.domain.entity.User;
import com.task.mx.service.UserService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller(UserController.URI)
@RequiredArgsConstructor
public class UserController {

    public static final String URI = "/users";

    @NonNull
    private final UserService userService;
    
    @NonNull
    @Post("/add")
    public User addNewUser(final @NonNull @Body User user) {
        return userService.addUser(user);
    }
}