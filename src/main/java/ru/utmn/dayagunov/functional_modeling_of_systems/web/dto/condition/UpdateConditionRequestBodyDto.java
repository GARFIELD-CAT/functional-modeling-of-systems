package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.condition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Operators;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public class UpdateConditionRequestBodyDto {

    @Schema(description = "ID условия")
    @NotNull(message = "ID условия должен быть указан.")
    private Integer id;

    @Schema(requiredMode = NOT_REQUIRED, description = "Название проверяемого поля",
            example = "countryOfCitizenship")
    @Size(max = 100)
    private String field;

    @Schema(requiredMode = NOT_REQUIRED, description = "Оператор сравнения. \n Возможные варианты: EQ - равно, NO_EQ - не равно" +
            ", GT - больше, LT - меньше,  GTE - больше или равно, LTE - меньше или равно, IN - содержит, NOT_IN - не содержит")
    private Operators operator;

    @Schema(requiredMode = NOT_REQUIRED, description = "Ожидаемое значение. Может принимать в качестве значения последовательность строк. \n" +
            "Например, 1,2,3. В таком случае проверка будет производиться по каждому значению", example = "1")
    @Size(max = 500)
    private String value;
}