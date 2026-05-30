package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.dto.UserResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(String login, String password) {
        Optional<User> result = userRepository.findByLoginIgnoreCase(login);

        if (result.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, String.format("Пользователь c таким login=%s уже существует.", login)
            );
        }

        User user = new User(login, passwordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", id)
                )
        );
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        return userRepository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Пользователь с login=%s не найден.", login)));
    }

    public UserResponseDto prepareUserResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();

        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());

        return userDto;
    }
}