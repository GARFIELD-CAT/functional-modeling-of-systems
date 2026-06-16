package ru.utmn.dayagunov.functional_modeling_of_systems.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.service.UserService;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.user.CreateUserRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.user.UserResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper.UserMapper;

@Tag(name = "Пользователи", description = "Создание пользователя и его просмотр (Доступно всем пользователям)")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Создает нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса", content = @Content),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким логином уже существует", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequestBodyDto body
    ) {
        User user = userService.createUser(body.getLogin(), body.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toResponseDto(user));
    }

    @Operation(summary = "Возвращает пользователя по его id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно найден",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable("id") Integer id
    ) {
        User user = userService.getUser(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toResponseDto(user));
    }

    @Operation(summary = "Возвращает текущего пользователя",
            description = "Возвращает профиль аутентифицированного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Текущий пользователь",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        User user = userService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toResponseDto(user));
    }
}
