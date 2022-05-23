package com.task.mx.api;

import com.task.mx.domain.entity.User;
import com.task.mx.service.UserService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Controller(UserRecordController.URI)
@Secured(SecurityRule.IS_AUTHENTICATED)
public record UserRecordController( UserService userService) {

    public static final String URI = "/users/v2";


    @NonNull
    @Post("/add")
    public HttpResponse<?> addNewUser(final @NonNull @Body User user) {
        final var result = userService.addUser(user);

        if (result.isSuccess()) {
            final var saved = result.getUser();
            final var location = HttpResponse.uri("%s/%s".formatted(URI, saved.id()));
            return HttpResponse.created(saved, location);
        }
        return userQueryFailure(result.getErrorMessage());
    }

    @NonNull
    @Get("/getAll")
    public HttpResponse<?> getAllUsers(){
        return HttpResponse.ok(userService.getAll());
    }

    @NonNull
    @Get("/getUser/{username}")
    public HttpResponse<?> findByUserName(final @NonNull @PathVariable String username){
        final var result = userService.getUserByUsername(username);

        if (result.isSuccess()){
            return HttpResponse.ok(result.getUser());
        }
        return usernameDontExist(result.getErrorMessage());

    }

    @NonNull
    private MutableHttpResponse<Map<String, String>> userQueryFailure(final @NonNull String failure) {
        log.warn("Failure found: {}", failure);
        final var body = Map.of(
                "message", failure
        );
        return HttpResponse.notFound(body);
    }

    @NonNull
    private MutableHttpResponse<Map<String, String>> usernameDontExist(final @NonNull String failure) {
        log.warn("Failure found: {}", failure);
        final var body = Map.of(
                "message", failure
        );
        return HttpResponse.notFound(body);
    }

}
