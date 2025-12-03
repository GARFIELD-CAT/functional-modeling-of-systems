package ru.utmn.dayagunov.functional_modeling_of_systems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.CreateUserRequestBody;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.UpdateUserRequestBody;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.UserResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.service.UserService;


@RestController
@RequestMapping("/api/users")
@Validated
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

        return new ResponseEntity<>(userService.prepareUserResponseDto(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Возвращает пользователя по его id")
    @GetMapping("/{id}")
    public UserResponseDto getUser(
            @PathVariable("id") Integer id
    ) {
        User user =  userService.getUser(id);

        return userService.prepareUserResponseDto(user);
    }

    @Operation(summary = "Обновляет одного пользователя по его id")
    @PutMapping
    public ResponseEntity<Object> updateUser(
            @Valid @RequestBody UpdateUserRequestBody updateUserRequestBody
    ) {
        User result = userService.updateUser(updateUserRequestBody);

        return new ResponseEntity<>(userService.prepareUserResponseDto(result), HttpStatus.OK);
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
