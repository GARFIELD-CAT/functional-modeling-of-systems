package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.CreateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.RuleConditionDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.UpdateRuleRequestBodyDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    @Transactional
    public Rule createRule(CreateRuleRequestBodyDto body) {
        Rule rule = new Rule();
        BeanUtils.copyProperties(body, rule, "conditions");
        rule.replaceConditions(buildConditions(body.getConditions()));

        return ruleRepository.save(rule);
    }

    @Transactional
    public Rule updateRule(UpdateRuleRequestBodyDto body) {
        Rule rule = findRuleById(body.getId());

        List<String> ignore = new ArrayList<>(Arrays.asList(getNullPropertyNames(body)));
        ignore.add("conditions");
        BeanUtils.copyProperties(body, rule, ignore.toArray(new String[0]));

        if (body.getConditions() != null) {
            rule.replaceConditions(buildConditions(body.getConditions()));
        }

        return ruleRepository.save(rule);
    }

    @Transactional(readOnly = true)
    public Rule getRule(Integer id) {
        return findRuleById(id);
    }

    @Transactional(readOnly = true)
    public List<Rule> getRules(boolean onlyActive) {
        if (onlyActive) {
            return ruleRepository.findEffectiveOn(LocalDate.now());
        }

        return ruleRepository.findAllWithConditions();
    }

    @Transactional
    public void deleteRule(Integer id) {
        Rule rule = findRuleById(id);
        ruleRepository.delete(rule);
    }

    private List<Condition> buildConditions(List<RuleConditionDto> dtos) {
        List<Condition> conditions = new ArrayList<>();

        if (dtos != null) {
            for (RuleConditionDto dto : dtos) {
                conditions.add(toCondition(dto));
            }
        }

        return conditions;
    }

    private Condition toCondition(RuleConditionDto dto) {
        Condition condition = new Condition();
        condition.setField(dto.getField());
        condition.setOperator(dto.getOperator());
        condition.setValue(dto.getValue());

        return condition;
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