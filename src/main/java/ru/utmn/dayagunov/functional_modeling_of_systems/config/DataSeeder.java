package ru.utmn.dayagunov.functional_modeling_of_systems.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.UserRoles;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUser("user",  "userPass",  UserRoles.USER);
        seedUser("admin", "adminPass", UserRoles.ADMIN);
    }

    private void seedUser(String login, String password, UserRoles role) {
        if (userRepository.findByLoginIgnoreCase(login).isPresent()) {
            log.info("Пользователь '{}' уже существует — пропуск", login);
            return;
        }
        User user = new User(login, passwordEncoder.encode(password));
        user.setRole(role.getDescription());
        userRepository.save(user);
        log.info("Создан пользователь '{}' с ролью {}", login, role);
    }
}