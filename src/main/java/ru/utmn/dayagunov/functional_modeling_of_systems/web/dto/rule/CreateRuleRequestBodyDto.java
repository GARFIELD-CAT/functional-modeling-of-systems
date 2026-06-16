package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public class CreateRuleRequestBodyDto {
    @Schema(requiredMode = REQUIRED, description = "Название правила")
    @NotBlank(message = "Название правила должно быть указано.")
    @Size(max = 255)
    private String title;

    @Schema(requiredMode = REQUIRED, description = "Описание правила")
    @NotBlank(message = "Описание правила должно быть указано.")
    @Size(max = 4000)
    private String description;

    @Schema(requiredMode = REQUIRED, description = "Что нужно получить (результат)")
    @NotBlank(message = "Требуемый результат должен быть указан.")
    @Size(max = 4000)
    private String requiredResult;

    @Schema(requiredMode = REQUIRED, description = "Что нужно сделать (действие)")
    @NotBlank(message = "Требуемое действие должно быть указано.")
    @Size(max = 4000)
    private String requiredAction;

    @Schema(requiredMode = REQUIRED, description = "Срок выполнения в днях", example = "30")
    @NotNull(message = "Срок должен быть указан.")
    @Positive(message = "Срок должен быть положительным числом.")
    private Integer period;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Дата окончания действия правила в формате yyyy-mm-dd (NULL = бессрочно)",
            example = "null")
    @FutureOrPresent(message = "Дата окончания действия правила не может быть в прошлом.")
    private LocalDate effectiveTo;

    @Valid
    @Schema(requiredMode = NOT_REQUIRED, description = "Условия применимости правила.")
    @Size(min = 1, max=20, message = "Список условий не может быть пустым.")
    private List<RuleConditionDto> conditions;
}