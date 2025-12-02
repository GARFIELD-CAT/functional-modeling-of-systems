package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.CreateUserRequestBody;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создает нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким логином уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<Object> createUser(
            @RequestBody CreateUserRequestBody body
    ) {
        User user = userService.createUser(body.getLogin(), body.getPassword());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает пользователя по его id")
    @GetMapping("/{id}")
    public User getUser(
            @PathVariable("id") Integer id
    ) {
        return userService.getUser(id);
    }

    @Operation(summary = "Обновляет одного пользователя по его id")
    @PutMapping
    public ResponseEntity<Object> updateUser(
            @RequestBody User user
    ) {
        User result = userService.updateUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет пользователя по его id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable("id") Integer id
    ) {
        userService.deleteUser(id);
    }
}
