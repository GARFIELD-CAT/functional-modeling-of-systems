package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule;

import lombok.Getter;
import lombok.Setter;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Operators;

@Getter
@Setter
public class ConditionResponseDto {
    private Integer id;
    private String field;
    private Operators operator;
    private String value;
}