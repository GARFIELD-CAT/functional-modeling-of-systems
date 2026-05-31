package ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto;

import lombok.Getter;
import lombok.Setter;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Operators;

@Getter
@Setter
public class ConditionResponseDto {
    private Integer id;
    private Integer ruleId;
    private String field;
    private Operators operator;
    private String value;
}