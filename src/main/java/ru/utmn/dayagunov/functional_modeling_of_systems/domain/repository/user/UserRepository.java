package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLoginIgnoreCase(@Param("login") String login);
}
