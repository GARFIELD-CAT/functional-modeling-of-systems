package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Логин должен быть указан.")
    @Size(min = 8, max = 30, message = "Размер должен быть от 8 до 30 символов")
    private String login;

    @Schema(
            requiredMode = REQUIRED,
            maxLength = 30,
            minLength = 8,
            description = "Пароль пользователя"
    )
    @NotNull(message = "Пароль должен быть указан.")
    @Size(min = 8, max = 30, message = "Размер должен быть от 8 до 30 символов")
    private String password;
}
