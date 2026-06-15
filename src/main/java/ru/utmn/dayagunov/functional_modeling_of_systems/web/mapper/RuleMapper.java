package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.condition.ConditionResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.RuleResponseDto;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RuleMapper {
    private final ConditionMapper conditionMapper;

    public RuleResponseDto toResponseDto(Rule rule) {
        RuleResponseDto dto = new RuleResponseDto();
        BeanUtils.copyProperties(rule, dto, "conditions");

        List<ConditionResponseDto> conditions = Optional.ofNullable(rule.getConditions())
                .orElse(List.of()).stream()
                .map(conditionMapper::toResponseDto)
                .toList();

        dto.setConditions(conditions);

        return dto;
    }
}
