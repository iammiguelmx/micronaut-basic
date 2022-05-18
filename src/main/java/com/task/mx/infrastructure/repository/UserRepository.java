package com.task.mx.infrastructure.repository;

import com.task.mx.domain.entity.User;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Contract to perform database operations
 */
@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
