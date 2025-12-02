package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public class CreateUserRequestBody {
    @Schema(requiredMode = REQUIRED,
            maxLength = 30,
            minLength = 8,
            description = "Логин пользователя"
    )
    private String login;

    @Schema(
            requiredMode = REQUIRED,
            maxLength = 30,
            minLength = 8,
            description = "Пароль пользователя"
    )
    private String password;
}
