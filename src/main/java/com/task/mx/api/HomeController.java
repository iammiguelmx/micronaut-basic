package com.task.mx.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
@Slf4j
public record HomeController() {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index(Principal principal) {
        log.info("Principal, {}:", principal.getName());
        return principal.getName();
    }
}
