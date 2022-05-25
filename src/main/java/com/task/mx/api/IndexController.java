package com.task.mx.api;

import io.micrometer.core.instrument.MeterRegistry;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;

@Controller
@RequiredArgsConstructor
@Secured(SecurityRule.IS_ANONYMOUS)
class IndexController {

    private final MeterRegistry meterRegistry;

    @Get("/hello/{name}")
    Mono<String> hello(@NotBlank String name) {
        meterRegistry
                .counter("web.access", "controller", "index", "action", "hello")
                .increment();
        return Mono.just("Hello " + name);
    }
}