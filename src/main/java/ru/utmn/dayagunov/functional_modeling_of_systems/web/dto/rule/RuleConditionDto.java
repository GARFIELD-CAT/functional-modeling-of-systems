package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Operators;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.validation.MigrantField;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public class RuleConditionDto {
    @Schema(requiredMode = REQUIRED, description = "Название проверяемого поля", example = "countryOfCitizenship")
    @NotBlank(message = "Поле должно быть указано.")
    @Size(max = 100)
    @MigrantField
    private String field;

    @Schema(requiredMode = REQUIRED, description = "Оператор сравнения. \n Возможные варианты: EQ - равно, NOT_EQ - не равно" +
            ", GT - больше, LT - меньше,  GTE - больше или равно, LTE - меньше или равно, IN - содержит, NOT_IN - не содержит")
    @NotNull(message = "Оператор должен быть указан.")
    private Operators operator;

    @Schema(requiredMode = REQUIRED, description = "Ожидаемое значение. Может принимать в качестве значения последовательность строк. \n" +
            "Например, 1,2,3. В таком случае проверка будет производиться по каждому значению", example = "1")
    @NotBlank(message = "Значение должно быть указано.")
    @Size(max = 500)
    private String value;
}