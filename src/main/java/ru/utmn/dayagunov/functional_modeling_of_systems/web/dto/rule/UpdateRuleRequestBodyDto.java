package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateRuleRequestBodyDto {
    @Schema(description = "ID правила")
    @NotNull(message = "ID правила должен быть указан.")
    private Integer id;

    @Schema(description = "Название правила")
    @Size(max = 255)
    private String title;

    @Schema(description = "Описание правила")
    @Size(max = 4000)
    private String description;

    @Schema(description = "Что нужно получить (результат)")
    @Size(max = 4000)
    private String requiredResult;

    @Schema(description = "Что нужно сделать (действие)")
    @Size(max = 4000)
    private String requiredAction;

    @Schema(description = "Срок выполнения в днях")
    @Positive(message = "Срок должен быть положительным числом.")
    private Integer period;

    @Schema(description = "Дата начала действия правила")
    private LocalDate effectiveFrom;

    @Schema(description = "Дата окончания действия правила (null = бессрочно)")
    @FutureOrPresent(message = "Дата окончания действия правила не может быть в прошлом.")
    private LocalDate effectiveTo;

    @Valid
    @Schema(description = "Полный новый список условий. null = условия не трогаем")
    @Size(min = 1, max=20, message = "Список условий не может быть пустым.")
    private List<RuleConditionDto> conditions;
}