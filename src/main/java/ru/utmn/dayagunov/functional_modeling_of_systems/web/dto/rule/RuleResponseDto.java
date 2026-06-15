package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RuleResponseDto {
    private Integer id;
    private String title;
    private String description;
    private String requiredResult;
    private String requiredAction;
    private Integer period;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private List<ConditionResponseDto> conditions;
}