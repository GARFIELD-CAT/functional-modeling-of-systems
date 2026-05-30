package ru.utmn.dayagunov.functional_modeling_of_systems.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLoginIgnoreCase(@Param("login") String login);
}
