package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.condition.CreateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.condition.UpdateConditionRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.ConditionRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;

import java.util.List;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.BeanCopyUtils.getNullPropertyNames;

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