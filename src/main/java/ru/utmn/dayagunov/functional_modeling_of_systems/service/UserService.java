package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(String login, String password) {
        Optional<User> result = findUserByLogin(login);

        if (result.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, String.format("Пользователь с таким login=%s уже существует.", login)
            );
        }

        User user = new User(login, password);

        repository.save(user);

        return user;
    }

    public Optional<User> findUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User updateUser(User user) {
        User userById = repository.findById(user.getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", user.getId())
                )
        );

        Optional<User> userByLogin = findUserByLogin(user.getLogin());

        if (userByLogin.isPresent()) {
            if (!userById.getId().equals(userByLogin.get().getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, String.format("Пользователь с таким login=%s уже существует", user.getLogin())
                );
            }
        }

        return repository.save(user);
    }

    public User getUser(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", id)
                )
        );
    }

    public void deleteUser(Integer id) {
        repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", id)
                )
            );

        repository.deleteById(id);
    }
}
