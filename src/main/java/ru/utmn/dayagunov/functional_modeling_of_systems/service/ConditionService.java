package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.ConditionResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.CreateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.UpdateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.condition.ConditionRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.rule.RuleRepository;

import java.util.List;
import java.util.Optional;

import static ru.utmn.dayagunov.functional_modeling_of_systems.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class ConditionService {
    private final ConditionRepository conditionRepository;
    private final RuleRepository ruleRepository;

    @Transactional
    public Condition createCondition(CreateConditionRequestBodyDto body) {
        Rule rule = findRuleById(body.getRuleId());

        Condition condition = new Condition();
        condition.setRule(rule);
        condition.setField(body.getField());
        condition.setOperator(body.getOperator());
        condition.setValue(body.getValue());

        return conditionRepository.save(condition);
    }

    @Transactional
    public Condition updateCondition(UpdateConditionRequestBodyDto body) {
        Condition condition = findConditionById(body.getId());
        BeanUtils.copyProperties(body, condition, getNullPropertyNames(body));

        if (body.getOperator() != null) {
            condition.setOperator(body.getOperator());
        }

        return conditionRepository.save(condition);
    }

    @Transactional(readOnly = true)
    public Condition getCondition(Integer id) {
        return findConditionById(id);
    }

    @Transactional(readOnly = true)
    public List<Condition> getConditionsByRuleId(Integer ruleId) {
        findRuleById(ruleId);
        return conditionRepository.findByRuleId(ruleId);
    }

    @Transactional
    public void deleteCondition(Integer id) {
        Condition condition = findConditionById(id);
        conditionRepository.delete(condition);
    }

    public ConditionResponseDto prepareConditionResponseDto(Condition condition) {
        ConditionResponseDto dto = new ConditionResponseDto();
        BeanUtils.copyProperties(condition, dto);
        dto.setRuleId(Optional.ofNullable(condition.getRule())
                .map(Rule::getId).orElse(null));

        return dto;
    }

    private Condition findConditionById(Integer id) {
        return conditionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Условие с id=%d не найдено", id)
                )
        );
    }

    private Rule findRuleById(Integer id) {
        return ruleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Правило с id=%d не найдено", id)
                )
        );
    }
}