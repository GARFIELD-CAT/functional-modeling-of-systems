package ru.utmn.dayagunov.functional_modeling_of_systems.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByLogin(String login);
}
