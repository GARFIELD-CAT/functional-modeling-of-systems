package ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.user.UserRepository;

import java.util.Optional;

public class JpaUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    PasswordEncoder encoder;

    public JpaUserDetailsService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.encoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginIgnoreCase(username);

        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.get().getLogin())
                    .password(user.get().getPassword())
                    .roles(user.get().getRole().getValue())
                    .build();
        }
        throw new UsernameNotFoundException(String.format("Пользователь с login=%s не найден.", username));
    }
}