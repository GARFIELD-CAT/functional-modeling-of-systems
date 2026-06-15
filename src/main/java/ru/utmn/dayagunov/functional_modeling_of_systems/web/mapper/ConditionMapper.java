package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.condition.ConditionResponseDto;

import java.util.Optional;

@Component
public class ConditionMapper {

    public ConditionResponseDto toResponseDto(Condition condition) {
        ConditionResponseDto dto = new ConditionResponseDto();
        BeanUtils.copyProperties(condition, dto);
        dto.setRuleId(Optional.ofNullable(condition.getRule())
                .map(Rule::getId).orElse(null));

        return dto;
    }
}
