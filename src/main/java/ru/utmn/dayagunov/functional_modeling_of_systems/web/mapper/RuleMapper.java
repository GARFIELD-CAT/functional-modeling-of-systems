package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.ConditionResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.RuleResponseDto;

import java.util.List;
import java.util.Optional;

@Component
public class RuleMapper {
    public RuleResponseDto toResponseDto(Rule rule) {
        RuleResponseDto dto = new RuleResponseDto();
        BeanUtils.copyProperties(rule, dto, "conditions");

        List<ConditionResponseDto> conditions = Optional.ofNullable(rule.getConditions())
                .orElse(List.of()).stream()
                .map(this::toConditionDto)
                .toList();

        dto.setConditions(conditions);

        return dto;
    }

    private ConditionResponseDto toConditionDto(Condition condition) {
        ConditionResponseDto dto = new ConditionResponseDto();
        BeanUtils.copyProperties(condition, dto);

        return dto;
    }
}
